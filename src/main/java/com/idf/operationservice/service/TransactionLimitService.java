package com.idf.operationservice.service;

import com.idf.operationservice.domain.dto.request.TransactionLimitDto;
import com.idf.operationservice.domain.entity.TransactionLimit;

/**
 * Сервис для работы с лимитами расходных операций
 */
public interface TransactionLimitService {

/**
 * Метод добавляет новый лимит по заданной категории
 */
    TransactionLimit addNewLimit(TransactionLimitDto transactionLimitDto);
}
