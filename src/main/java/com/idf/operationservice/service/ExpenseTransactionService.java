package com.idf.operationservice.service;

import com.idf.operationservice.domain.dto.request.ExpenseTransactionDto;
import com.idf.operationservice.domain.projection.ExceedingLimitResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для работы с транзакциями
 */
public interface ExpenseTransactionService {

    /**
     * ервис для обработки входящих данных о транзакции
     */
    void processTransaction(ExpenseTransactionDto transaction);

    /**
     * Метод для получения списка всех транзакций, по которым был превышен лимит
     */
    List<ExceedingLimitResponse> findExceedingLimitTransactions();

    /**
     * Метод для расчета актуальной суммы расходов
     */
    BigDecimal totalSumByCategory(ExpenseTransactionDto transaction);
}
