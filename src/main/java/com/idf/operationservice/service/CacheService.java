package com.idf.operationservice.service;

import com.idf.operationservice.domain.dto.request.TransactionLimitDto;
import java.math.BigDecimal;

/**
 * Сервис для работв с кэшом в Редис
 */
public interface CacheService {

    /**
     * Метод обновляет закэшированные данные по лимиту расходов
     */
    void cacheNewLimit(TransactionLimitDto transactionLimitDto);

    /**
     * Метод для поиска закэшированного лимита по данной категории расходов
     */
    BigDecimal findLimitByCategory(String key);

    /**
     * Метод для кэширования суммы операций по данной категории
     */
    void cacheTotalSum(String category, BigDecimal totalSum);

    /**
     * Метод ищет закэшированную сумму транзакций по данной категории
     */
    BigDecimal findTotalSum(String category);

    /**
     * Планировщик для инвалидации кэша
     */
    void resetCache();
}
