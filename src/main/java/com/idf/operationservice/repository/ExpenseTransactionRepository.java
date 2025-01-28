package com.idf.operationservice.repository;

import com.idf.operationservice.domain.entity.ExpenseTransaction;
import com.idf.operationservice.domain.projection.ExceedingLimitResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для осуществления операций чтения и записи данных о транзакциях в базу данных
 */
public interface ExpenseTransactionRepository extends JpaRepository<ExpenseTransaction, UUID> {

    /**
     * Метод получает сумму транзакций по заданной категории и заданному периоду
     */
    @Query("""
        SELECT SUM(e.transactionSum)
        FROM ExpenseTransaction e
        WHERE e.category = :category AND e.transactionTime >= :startOfMonth
        """)
    Optional<BigDecimal> sumTransactionsByCategoryAndDate(String category, ZonedDateTime startOfMonth);

    /**
     * Метод возвращает список обьектов, содержащих данные о транзакциях, превысивших лимит,
     * и актуальные на момент ее осуществления лимит.
     * Данные аккумулируются из 3 таблиц(ExpenseTransaction и TransactionLimit)
     */
    @Query(value = """
           SELECT new com.idf.operationservice.domain.projection.ExceedingLimitResponse
           (et.accountFrom, et.accountTo, et.currency, et.category, et.transactionSum, 
           et.transactionTime, et.restLimitSum, rl.limitValue, rl.limitCurrency, rl.limitSettingTime) 
                  FROM ExpenseTransaction et
                  JOIN TransactionLimit rl ON et.category = rl.category
                  WHERE et.limitExceeded = true
                  AND rl.limitSettingTime=(
                  SELECT MAX(rl2.limitSettingTime)
                  FROM TransactionLimit rl2
                  WHERE rl2.category = et.category
                 AND rl2.limitSettingTime <= et.transactionTime)          
        """)
    List<ExceedingLimitResponse> findExceedingLimits();}
