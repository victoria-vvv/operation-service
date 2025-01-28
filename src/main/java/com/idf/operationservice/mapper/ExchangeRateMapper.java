package com.idf.operationservice.mapper;

import com.idf.operationservice.domain.entity.ExchangeRate;

import com.idf.operationservice.domain.dto.response.ExchangeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Маппер для преобразования Dto курса валют в Entity
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExchangeRateMapper {

    ExchangeRate mapToExchangeRate(ExchangeRateResponse response);
}
