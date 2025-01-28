package com.idf.operationservice.domain.entity;

import com.idf.operationservice.util.RegExp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Данные расходной транзакции
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ExpenseTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private UUID id;

    @Digits(integer = 10, fraction = 0, message = "Банковский счет должен содержать 10 цифр")
    private Long accountFrom;

    @Digits(integer = 10, fraction = 0, message = "Банковский счет должен содержать 10 цифр")
    private Long accountTo;


    @Schema(description = "Сумма транзакции", pattern = RegExp.expenseFormat, example = "1000.00")
    private BigDecimal transactionSum;

    @NotBlank
    private String currency;

    @Column(insertable = false)
    private ZonedDateTime transactionTime;

    @NotBlank
    private String category;

    @Schema(description = "Сумма остатка лимита", pattern = RegExp.expenseFormat, example = "1000.00")
    private BigDecimal restLimitSum;

    private Boolean limitExceeded;
}
