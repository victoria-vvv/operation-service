package com.idf.operationservice.domain.entity;

import com.idf.operationservice.util.RegExp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Лимит расходов по определенной категории
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private UUID id;

    @NotBlank
    private String category;

    @Min(value = 0, message = "Лимит не может быть отрицательным числом.")
    @Schema(description = "Сумма лимита", pattern = RegExp.expenseFormat, example = "1000.00")
    private BigDecimal limitValue;

    private String limitCurrency;

    @Column(insertable = false)
    private ZonedDateTime limitSettingTime;
}
