package com.idf.operationservice.repository;

import com.idf.operationservice.domain.entity.TransactionLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий предоставляет методы для выполнения запросов к базе данных, связанным с лимитами по транзакциям клиента.
 */
public interface TransactionLimitRepository extends JpaRepository<TransactionLimit, UUID> {

    /**
     * Выполняет запрос к базе данных для получения последнего добавленного лимита по транзакциям
     */
    @Query("""
        SELECT t.limitValue
        FROM TransactionLimit t 
        WHERE t.category = :category 
        ORDER BY t.limitSettingTime DESC
        LIMIT 1
        """)
    Optional<BigDecimal> findLatestLimitByCategory(String category);
}
