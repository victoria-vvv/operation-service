package com.idf.operationservice.domain.dto.response;

import com.idf.operationservice.util.RegExp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO для передачи информации о текущем курсе валюты.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {

    @Schema(description = "Символ валютной пары", pattern = RegExp.currencyPairFormat, example = "KZT/USD")
    private String symbol;

    @Schema(description = "Текущий курс валютной пары", pattern = RegExp.currencyValueFormat, example = "1500.12")
    private BigDecimal exchangeRate;

    @Schema(description = "Курс последнего закрытия валютной пары", pattern = RegExp.currencyValueFormat, example = "1500.12")
    private BigDecimal previousCloseRate;

    @Schema(description = "Текущая дата установленного курса", example = "15.01.2022")
    private LocalDate date;
}
