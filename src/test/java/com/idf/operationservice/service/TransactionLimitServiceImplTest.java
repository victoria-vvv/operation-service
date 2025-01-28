package com.idf.operationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.idf.operationservice.domain.dto.request.TransactionLimitDto;
import com.idf.operationservice.domain.entity.TransactionLimit;
import com.idf.operationservice.mapper.TransactionLimitMapper;
import com.idf.operationservice.repository.TransactionLimitRepository;
import com.idf.operationservice.service.impl.CacheServiceImpl;
import com.idf.operationservice.service.impl.TransactionLimitServiceImpl;
import com.idf.operationservice.util.ConstantUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionLimitServiceImplTest {

    @Mock
    private TransactionLimitRepository transactionLimitRepository;
    @Mock
    private TransactionLimitMapper transactionLimitMapper;
    @Mock
    private CacheServiceImpl cacheService;
    @InjectMocks
    private TransactionLimitServiceImpl transactionLimitService;

    private TransactionLimitDto transactionLimitDto;
    private TransactionLimit transactionLimit;

    @BeforeEach
    void setUp() {
        transactionLimitDto = TransactionLimitDto.builder()
            .category(ConstantUtil.PRODUCT_CATEGORY)
            .limitValue(ConstantUtil.PRODUCT_LIMIT_2).build();

        transactionLimit = TransactionLimit.builder()
            .category(ConstantUtil.PRODUCT_CATEGORY)
            .limitValue(ConstantUtil.PRODUCT_LIMIT_2).build();
    }

    @Test
    @DisplayName("Limit added successfully")
    void addNewLimitSuccessfully() {
        when(transactionLimitMapper.mapToTransactionLimit(transactionLimitDto)).thenReturn(transactionLimit);
        when(transactionLimitRepository.save(transactionLimit)).thenReturn(transactionLimit);

        TransactionLimit result = transactionLimitService.addNewLimit(transactionLimitDto);

        assertNotNull(result);
        assertEquals(transactionLimit, result);

        verify(transactionLimitMapper).mapToTransactionLimit(transactionLimitDto);
        verify(transactionLimitRepository).save(transactionLimit);
        verify(cacheService).cacheNewLimit(transactionLimitDto);
    }
}
