package com.idf.operationservice.service.impl;

import com.idf.operationservice.domain.entity.ExpenseTransaction;
import com.idf.operationservice.domain.projection.ExceedingLimitResponse;
import com.idf.operationservice.mapper.ExpenseTransactionMapper;
import com.idf.operationservice.repository.TransactionLimitRepository;
import com.idf.operationservice.repository.ExpenseTransactionRepository;
import com.idf.operationservice.domain.dto.request.ExpenseTransactionDto;
import com.idf.operationservice.domain.dto.request.TransactionLimitDto;
import com.idf.operationservice.service.ExpenseTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseTransactionServiceImpl implements ExpenseTransactionService {

    private final CacheServiceImpl cacheService;
    private final CurrencyConversionServiceImpl conversionService;
    private final ExpenseTransactionRepository transactionRepository;
    private final TransactionLimitRepository limitRepository;
    private final ExpenseTransactionMapper transactionMapper;

    @Value("${prefix.key.limit}")
    private String limitKeyPrefix;

    /**
     * Метод сохраняет транзакцию, включая данные по остатку лимита
     */
    @Transactional
    public void processTransaction(ExpenseTransactionDto transactionDto) {
        BigDecimal limit = limitForTransaction(transactionDto);

        BigDecimal totalSum = totalSumByCategory(transactionDto);

        BigDecimal transactionSumInUSD = conversionService.convertToUSD(transactionDto.getTransactionSum(),
            transactionDto.getCurrency(), LocalDate.now());
        BigDecimal newTotalSum = totalSum.add(transactionSumInUSD);
        BigDecimal transactionLimitRest = limit.subtract(newTotalSum);
        boolean isLimitExceeded = newTotalSum.compareTo(limit) > 0;

        ExpenseTransaction transaction = transactionMapper.mapToTransaction(transactionDto);

        transaction.setRestLimitSum(transactionLimitRest);
        transaction.setLimitExceeded(isLimitExceeded);

        transactionRepository.save(transaction);

        cacheService.cacheTotalSum(transactionDto.getCategory(), newTotalSum);
    }

    /**
     * Метод получает лимит по транзакциям из кэша. Если закэщированных данных нет,
     * идет запрос в базу данных. После чего лимит кэшируется.
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public BigDecimal limitForTransaction(ExpenseTransactionDto transaction) {
        String limitKey = limitKeyPrefix + transaction.getCategory();

        BigDecimal limit = cacheService.findLimitByCategory(limitKey);

        if (limit == null) {
            limit = limitRepository.findLatestLimitByCategory(transaction.getCategory())
                .orElse(BigDecimal.ZERO);

            cacheService.cacheNewLimit(TransactionLimitDto.builder()
                .category(transaction.getCategory()).limitValue(limit).build());
        }
        return limit;
    }

    /**
     * Метод возвращаем сумму расходов по категории расходов, по которой осуществлена транзакция
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BigDecimal totalSumByCategory(ExpenseTransactionDto transaction) {
        BigDecimal totalSum = cacheService.findTotalSum(transaction.getCategory());

        if (totalSum == null) {
            ZonedDateTime firstDayOfMonth = LocalDate.now()
                .withDayOfMonth(1).atStartOfDay().atZone(ZoneId.systemDefault());

            totalSum = transactionRepository.sumTransactionsByCategoryAndDate(transaction.getCategory(), firstDayOfMonth)
                .orElse(BigDecimal.ZERO);

            cacheService.cacheTotalSum(transaction.getCategory(), totalSum);
        }
        return totalSum;
    }

    /**
     * Метод для получения списка транзакций, по которым превышен лимит
     */
    @Transactional(readOnly = true)
    public List<ExceedingLimitResponse> findExceedingLimitTransactions() {
        List<ExceedingLimitResponse> exceedingLimitTransactions = transactionRepository.findExceedingLimits();

        if (exceedingLimitTransactions.isEmpty()) {
            log.info("Транзакций, превышающих лимит, не найдено");
        }
        return exceedingLimitTransactions;
    }
}
