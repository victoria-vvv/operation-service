package com.idf.operationservice.domain.dto.request;

import com.idf.operationservice.util.RegExp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * Dto для входящих транзакций
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseTransactionDto {

    @Schema(description = "Сумма транзакции", pattern = RegExp.expenseFormat, example = "1000.00")
    private BigDecimal transactionSum;

    @Digits(integer = 10, fraction = 0)
    @Schema(description = "Банковский счет отправителя", example = "1034567812")
    private Long accountFrom;

    @Digits(integer = 10, fraction = 0)
    @Schema(description = "Банковский счет получателя", example = "102245678911")
    private Long accountTo;

    @Schema(description = "Валюта транзакции", pattern = RegExp.currencyNameFormat, example = "USD")
    private String currency;

    @Schema(description = "Категория расходов", example = "Product")
    private String category;
}
