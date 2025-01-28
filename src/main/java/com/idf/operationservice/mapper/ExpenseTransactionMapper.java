package com.idf.operationservice.mapper;

import com.idf.operationservice.domain.dto.request.ExpenseTransactionDto;
import com.idf.operationservice.domain.entity.ExpenseTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Маппер для преобразования Dto входящей транзакции в Entity
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpenseTransactionMapper {

    ExpenseTransaction mapToTransaction(ExpenseTransactionDto transactionDto);
}
