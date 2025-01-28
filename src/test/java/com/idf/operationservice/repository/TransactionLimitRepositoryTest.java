package com.idf.operationservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.idf.operationservice.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;

@Slf4j
class TransactionLimitRepositoryTest extends PostgreSQLTestContainer {

    @Autowired
    TransactionLimitRepository transactionLimitRepository;

    @Test
    @DisplayName("Find relevant transaction limit by category")
    void findTransactionLimitByCategorySuccessfully() {

        BigDecimal productLimitValue =
            transactionLimitRepository.findLatestLimitByCategory(ConstantUtil.PRODUCT_CATEGORY).get();

        BigDecimal serviceLimitValue =
            transactionLimitRepository.findLatestLimitByCategory(ConstantUtil.SERVICE_CATEGORY).get();

        assertEquals(ConstantUtil.LAST_LIMIT_VALUE, productLimitValue);
        assertEquals(ConstantUtil.LAST_LIMIT_VALUE, serviceLimitValue);
    }
}
