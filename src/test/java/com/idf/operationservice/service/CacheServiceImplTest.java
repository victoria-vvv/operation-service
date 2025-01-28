package com.idf.operationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.idf.operationservice.domain.dto.request.TransactionLimitDto;
import com.idf.operationservice.service.impl.CacheServiceImpl;
import com.idf.operationservice.util.ConstantUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CacheServiceImplTest {

    @Mock
    private RedisTemplate<String, BigDecimal> redisTemplate;
    @Mock
    private ValueOperations<String, BigDecimal> valueOperations;
    @InjectMocks
    private CacheServiceImpl cacheService;

    @Value("${prefix.key.limit}")
    private String limitKeyPrefix;

    @Value("${prefix.key.sum}")
    private String sumKeyPrefix;

    @BeforeEach
    void setUp(){
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("New limit cached successfully")
    void cacheNewLimitSuccessfully() {
        TransactionLimitDto transactionLimitDto = TransactionLimitDto.builder()
            .category(ConstantUtil.PRODUCT_CATEGORY).limitValue(ConstantUtil.PRODUCT_LIMIT_2).build();

        cacheService.cacheNewLimit(transactionLimitDto);

        verify(valueOperations).set(limitKeyPrefix + ConstantUtil.PRODUCT_CATEGORY, transactionLimitDto.getLimitValue());
    }

    @Test
    @DisplayName("Find cached limit successfully")
    void findCachedLimitSuccessfully() {
        BigDecimal expectedLimit = ConstantUtil.LAST_LIMIT_VALUE;
        when(valueOperations.get(limitKeyPrefix + ConstantUtil.PRODUCT_CATEGORY)).thenReturn(ConstantUtil.PRODUCT_LIMIT_1);

        BigDecimal actualLimit = cacheService.findLimitByCategory(limitKeyPrefix + ConstantUtil.PRODUCT_CATEGORY);

        assertEquals(expectedLimit, actualLimit);
        verify(valueOperations).get(limitKeyPrefix + ConstantUtil.PRODUCT_CATEGORY);
    }

    @Test
    @DisplayName("New sum cached successfully")
    void cacheTotalSumSuccessfully() {
        cacheService.cacheTotalSum(ConstantUtil.SERVICE_CATEGORY, ConstantUtil.SERVICE_TOTAL_SUM);

        String sumKey = sumKeyPrefix + ConstantUtil.SERVICE_CATEGORY;
        verify(valueOperations).set(sumKey, ConstantUtil.SERVICE_TOTAL_SUM);
    }

    @Test
    @DisplayName("Find cached sum successfully")
    void findCachedTotalSumSuccessfully() {
        when(valueOperations.get(sumKeyPrefix + ConstantUtil.SERVICE_CATEGORY))
            .thenReturn(ConstantUtil.SERVICE_TOTAL_SUM);

        BigDecimal actualTotalSum = cacheService.findTotalSum(ConstantUtil.SERVICE_CATEGORY);

        assertEquals(ConstantUtil.EXPECTED_SERVICE_SUM, actualTotalSum);
        verify(valueOperations).get(sumKeyPrefix + ConstantUtil.SERVICE_CATEGORY);
    }
}
