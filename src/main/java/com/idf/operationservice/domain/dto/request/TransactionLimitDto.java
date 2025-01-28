package com.idf.operationservice.domain.dto.request;

import com.idf.operationservice.util.RegExp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * Dto для запроса на изменение месячного лимита операций по заданной категории расходов
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLimitDto {

    @Schema(description = "Категория расходов", example = "Product")
    private String category;

    @Schema(description = "Сумма лимита", pattern = RegExp.expenseFormat, example = "1000.00")
    @Min(value = 0, message = "Лимит не может быть отрицательным числом.")
    private BigDecimal limitValue;
}
