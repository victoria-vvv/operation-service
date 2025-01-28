package com.idf.operationservice.repository;

import com.idf.operationservice.domain.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для осуществления операций чтения и записи данных о курсах валют базу данны
 */
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {

    /**
     * Метод ищет актуальную котировку заданной валютной пары
     */
    @Query("""
        SELECT e
        FROM ExchangeRate e
        WHERE e.currencyPair = :currencyPair
        AND e.date = (
        SELECT MAX(date)
        FROM ExchangeRate 
        WHERE currencyPair = :currencyPair 
        AND date <= :date)
        """)
    Optional<ExchangeRate> findByCurrencyPairAndLocalDate(String currencyPair, LocalDate date);
}