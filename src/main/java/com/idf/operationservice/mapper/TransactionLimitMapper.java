package com.idf.operationservice.mapper;

import com.idf.operationservice.domain.entity.TransactionLimit;
import com.idf.operationservice.domain.dto.request.TransactionLimitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Маппер для взаимного преобразования Dto лимита операций в Entity
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionLimitMapper {

    @Mapping(target = "limitCurrency", constant = "USD")
    TransactionLimit mapToTransactionLimit(TransactionLimitDto transactionLimitDto);
}
