package com.idf.operationservice.service;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.idf.operationservice.domain.dto.request.ExpenseTransactionDto;
import com.idf.operationservice.domain.entity.ExpenseTransaction;
import com.idf.operationservice.mapper.ExpenseTransactionMapper;
import com.idf.operationservice.repository.ExpenseTransactionRepository;
import com.idf.operationservice.repository.TransactionLimitRepository;
import com.idf.operationservice.service.impl.CacheServiceImpl;
import com.idf.operationservice.service.impl.CurrencyConversionServiceImpl;
import com.idf.operationservice.service.impl.ExpenseTransactionServiceImpl;
import com.idf.operationservice.util.ConstantUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ExpenseTransactionServiceImplTest {

    @Mock
    private CacheServiceImpl cacheService;
    @Mock
    private CurrencyConversionServiceImpl conversionService;
    @Mock
    private ExpenseTransactionRepository transactionRepository;
    @Mock
    private TransactionLimitRepository limitRepository;
    @Mock
    private ExpenseTransactionMapper transactionMapper;
    @InjectMocks
    private ExpenseTransactionServiceImpl expenseTransactionService;

    @Value("${prefix.key.limit}")
    private String limitKey;

    @Test
    @DisplayName("Process transaction with limit not exceeded")
    void processTransactionSuccessfully() {
        ExpenseTransactionDto transactionDto = ExpenseTransactionDto.builder().category(ConstantUtil.PRODUCT_CATEGORY)
            .transactionSum(ConstantUtil.TRANSACTION_SUM_1).currency(ConstantUtil.KZT).build();

        BigDecimal newTotalSum = ConstantUtil.PRODUCT_TOTAL_SUM.add(ConstantUtil.KZT_TRANSACTION_IN_USD);
        BigDecimal restLimit = ConstantUtil.PRODUCT_LIMIT_2.subtract(newTotalSum);

        ExpenseTransaction transaction = new ExpenseTransaction();

        when(cacheService.findLimitByCategory(limitKey+ ConstantUtil.PRODUCT_CATEGORY))
       .thenReturn(ConstantUtil.PRODUCT_LIMIT_2);
        when(cacheService.findTotalSum(ConstantUtil.PRODUCT_CATEGORY)).thenReturn(ConstantUtil.PRODUCT_TOTAL_SUM);
        when(conversionService.convertToUSD(ConstantUtil.TRANSACTION_SUM_1, ConstantUtil.KZT, LocalDate.now()))
            .thenReturn(ConstantUtil.KZT_TRANSACTION_IN_USD);
        when(transactionMapper.mapToTransaction(transactionDto)).thenReturn(transaction);

        expenseTransactionService.processTransaction(transactionDto);

        ArgumentCaptor<ExpenseTransaction> captor = ArgumentCaptor.forClass(ExpenseTransaction.class);
        verify(transactionRepository).save(captor.capture());

        ExpenseTransaction savedTransaction = captor.getValue();

        assertEquals(restLimit, savedTransaction.getRestLimitSum());
        assertFalse(savedTransaction.getLimitExceeded());
        verify(cacheService).cacheTotalSum(ConstantUtil.PRODUCT_CATEGORY, newTotalSum);
    }

    @Test
    @DisplayName("Process transaction with limit exceeded")
    void processTransactionLimitExceeded() {
        ExpenseTransactionDto transactionDto = ExpenseTransactionDto.builder().category(ConstantUtil.SERVICE_CATEGORY)
            .transactionSum(ConstantUtil.TRANSACTION_SUM_2).currency(ConstantUtil.KZT).build();

        ExpenseTransaction transaction = new ExpenseTransaction();

        when(cacheService.findLimitByCategory(limitKey + ConstantUtil.SERVICE_CATEGORY))
            .thenReturn(ConstantUtil.SERVICE_LIMIT_2);
        when(cacheService.findTotalSum(ConstantUtil.SERVICE_CATEGORY)).thenReturn(ConstantUtil.SERVICE_TOTAL_SUM);
        when(conversionService.convertToUSD(ConstantUtil.TRANSACTION_SUM_2, ConstantUtil.KZT, LocalDate.now()))
            .thenReturn(ConstantUtil.KZT_TRANSACTION_IN_USD);
        when(transactionMapper.mapToTransaction(transactionDto)).thenReturn(transaction);

        expenseTransactionService.processTransaction(transactionDto);

        ArgumentCaptor<ExpenseTransaction> captor = ArgumentCaptor.forClass(ExpenseTransaction.class);

        verify(transactionRepository).save(captor.capture());

        ExpenseTransaction savedTransaction = captor.getValue();

        assertTrue(savedTransaction.getLimitExceeded());
    }
}