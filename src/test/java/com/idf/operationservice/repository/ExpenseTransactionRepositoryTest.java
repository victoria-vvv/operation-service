package com.idf.operationservice.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.idf.operationservice.domain.projection.ExceedingLimitResponse;
import com.idf.operationservice.util.ConstantUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

class ExpenseTransactionRepositoryTest extends PostgreSQLTestContainer {

    @Autowired
    ExpenseTransactionRepository expenseTransactionRepository;

    @Test
    @DisplayName("Find transaction with Limit Exceed")
    void findLimitExceedTransactionsSuccessfully() {
        List<ExceedingLimitResponse> actualResult = expenseTransactionRepository.findExceedingLimits();

        List<ExceedingLimitResponse> expectedResult = List.of(
            ExceedingLimitResponse.builder().accountFrom(ConstantUtil.ACCOUNT_FROM).accountTo(ConstantUtil.ACCOUNT_TO)
                .currency(ConstantUtil.KZT).category(ConstantUtil.PRODUCT_CATEGORY).transactionSum(ConstantUtil.TRANSACTION_SUM_1)
                .transactionTime(ConstantUtil.TRANSACTION_TIME_1).restLimitSum(ConstantUtil.REST_LIMIT_1)
                .limitValue(ConstantUtil.PRODUCT_LIMIT_1).limitCurrency(ConstantUtil.USD)
                .limitSettingTime(ConstantUtil.LIMIT_SET_TIME_1).build(),

            ExceedingLimitResponse.builder().accountFrom(ConstantUtil.ACCOUNT_FROM).accountTo(ConstantUtil.ACCOUNT_TO)
                .currency(ConstantUtil.USD).category(ConstantUtil.PRODUCT_CATEGORY).transactionSum(ConstantUtil.TRANSACTION_SUM_3)
                .transactionTime(ConstantUtil.TRANSACTION_TIME_2).restLimitSum(ConstantUtil.REST_LIMIT_3)
                .limitValue(ConstantUtil.PRODUCT_LIMIT_2).limitCurrency(ConstantUtil.USD)
                .limitSettingTime(ConstantUtil.LIMIT_SET_TIME_2).build(),

            ExceedingLimitResponse.builder().accountFrom(ConstantUtil.ACCOUNT_FROM).accountTo(ConstantUtil.ACCOUNT_TO)
                .currency(ConstantUtil.RUB).category(ConstantUtil.SERVICE_CATEGORY).transactionSum(ConstantUtil.TRANSACTION_SUM_2)
                .transactionTime(ConstantUtil.TRANSACTION_TIME_1).restLimitSum(ConstantUtil.REST_LIMIT_2)
                .limitValue(ConstantUtil.SERVICE_LIMIT_1).limitCurrency(ConstantUtil.USD)
                .limitSettingTime(ConstantUtil.LIMIT_SET_TIME_1).build(),

            ExceedingLimitResponse.builder().accountFrom(ConstantUtil.ACCOUNT_FROM).accountTo(ConstantUtil.ACCOUNT_TO)
                .currency(ConstantUtil.USD).category(ConstantUtil.SERVICE_CATEGORY).transactionSum(ConstantUtil.TRANSACTION_SUM_4)
                .transactionTime(ConstantUtil.TRANSACTION_TIME_2).restLimitSum(ConstantUtil.REST_LIMIT_4)
                .limitValue(ConstantUtil.SERVICE_LIMIT_2).limitCurrency(ConstantUtil.USD).limitSettingTime(ConstantUtil.LIMIT_SET_TIME_2).build());

        assertEquals(4, actualResult.size());
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    @DisplayName("Find transactions' sum by categories")
    void findTransactionSumByCategoriesSuccessfully() {
        ZonedDateTime currentTime = LocalDate.now()
            .withDayOfMonth(1).atStartOfDay().atZone(ZoneId.systemDefault());

        Optional<BigDecimal> productSum
            = expenseTransactionRepository.sumTransactionsByCategoryAndDate(ConstantUtil.PRODUCT_CATEGORY, currentTime);

        Optional<BigDecimal> serviceSum
            = expenseTransactionRepository.sumTransactionsByCategoryAndDate(ConstantUtil.SERVICE_CATEGORY, currentTime);

        assertAll(
            () -> assertTrue(productSum.isPresent()),
            () -> assertTrue(serviceSum.isPresent()),
            () -> assertEquals(ConstantUtil.PRODUCT_SUM, productSum.get().doubleValue()),
            () -> assertEquals(ConstantUtil.SERVICE_SUM, serviceSum.get().doubleValue())
        );
    }
}
