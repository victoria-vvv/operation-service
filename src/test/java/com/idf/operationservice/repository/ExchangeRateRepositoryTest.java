package com.idf.operationservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.idf.operationservice.util.ConstantUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.time.LocalDate;

class ExchangeRateRepositoryTest extends PostgreSQLTestContainer {

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    @Test
    @DisplayName("Find relevant currency pair value")
    void findRelevantCurrencyPairSuccessfully() {

        BigDecimal kztUsdPair = exchangeRateRepository.
            findByCurrencyPairAndLocalDate(ConstantUtil.KZT_USD_PAIR, LocalDate.now()).get().getRate();

        BigDecimal rubUsdPair = exchangeRateRepository.
            findByCurrencyPairAndLocalDate(ConstantUtil.RUB_USD_PAIR, LocalDate.now()).get().getRate();

        assertEquals(ConstantUtil.KZT_USD_VALUE_CLOSE, kztUsdPair);
        assertEquals(ConstantUtil.RUB_USD_VALUE_CLOSE, rubUsdPair);
    }
}
