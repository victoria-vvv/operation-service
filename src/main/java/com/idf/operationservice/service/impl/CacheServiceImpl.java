package com.idf.operationservice.service.impl;

import com.idf.operationservice.domain.dto.request.TransactionLimitDto;
import com.idf.operationservice.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Сервис, реализующий методы для работы с данными, закэшированными в Redis
 */
@Component
@RequiredArgsConstructor
@PropertySource(value = "/application-prod.yaml")
public class CacheServiceImpl implements CacheService {

    private final RedisTemplate<String, BigDecimal> redisTemplate;

    @Value("${prefix.key.limit}")
    private String limitKeyPrefix;

    @Value("${prefix.key.sum}")
    private String sumKeyPrefix;
    private final String ALL_KEYS = "*";

    /**
     * Метод кэширует новый лимит по заданной сумме расходов
     */
    public  void cacheNewLimit(TransactionLimitDto transactionLimitDto){
        String key = limitKeyPrefix + transactionLimitDto.getCategory();
        redisTemplate.opsForValue().set(key, transactionLimitDto.getLimitValue());
    }

    /**
     * Метод для поиска закэшированного лимита по данной категории расходов
     *
     * @param key ключ, содержащий категорию расходов, по которой нужно найти закэшированный лимит
     * @return лимит по данной категории расходов
     */
    public BigDecimal findLimitByCategory(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Метод для кэширования суммы операций по данной категории
     *
     * @param category категория расходов, по которой потрачена закэшированная сумма
     * @param totalSum сумма закэшированного расхода
     */
    public void cacheTotalSum(String category, BigDecimal totalSum) {
        String sumKey = sumKeyPrefix + category;
        redisTemplate.opsForValue().set(sumKey, totalSum);
    }

    /**
     * Метод ищет закэшированную сумму транзакций по данной категории
     *
     * @param category категория расходов, по которй нужно найти сумму операций
     * @return сумма транзакций
     */
    public BigDecimal findTotalSum(String category) {
        String sumKey = sumKeyPrefix + category;
        return redisTemplate.opsForValue().get(sumKey);
    }

      /**
     * Планировщик обнуляет закэшированное значение сумм транзакций каждое 1 число месяца в 00:00
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetCache() {
        String key = sumKeyPrefix;

        Objects.requireNonNull(redisTemplate.keys(key + ALL_KEYS))
            .stream().filter(Objects::nonNull)
            .forEach(redisTemplate::delete);
    }
}
