package com.idf.operationservice.domain.projection;

import com.idf.operationservice.util.RegExp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Проекция предоставляет информацию о транзакциях, по которым был превышен лимит.
 * Параметры извлекаются из таблиц ExpenseTransaction и TransactionLimit.
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExceedingLimitResponse {

    @Schema(description = "Банковский счет отправителя", pattern = RegExp.accountFormat, example = "1034567812")
    private Long accountFrom;

    @Schema(description = "Банковский счет получателя", pattern = RegExp.accountFormat, example = "102245678911")
    private Long accountTo;

    @Schema(description = "Валюта транзакции", pattern = RegExp.currencyNameFormat, example = "USD")
    private String currency;

    @Schema(description = "Категория расходов", example = "Product")
    private String category;

    @Schema(description = "Сумма транзакции", pattern = RegExp.expenseFormat, example = "123.12")
    private BigDecimal transactionSum;

    @Schema(description = "Время осуществления транзакции", example = "2022-01-10 00:00:00+06")
    private ZonedDateTime transactionTime;

    @Schema(description = "Остаток месячного лимита", pattern = RegExp.expenseFormat, example = "200.00")
    private BigDecimal restLimitSum;

    @Schema(description = "Устанволеннвй месячный лимит", pattern = RegExp.expenseFormat, example = "200.00")
    private BigDecimal limitValue;

    @Schema(description = "Валюта транзакции", pattern = RegExp.currencyNameFormat, example = "USD")
    private String limitCurrency;

    @Schema(description = "Время установления лимита", example = "2022-01-10 00:00:00+06")
    private ZonedDateTime limitSettingTime;
}
