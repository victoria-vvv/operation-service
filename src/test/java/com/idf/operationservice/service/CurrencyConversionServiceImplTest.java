package com.idf.operationservice.service;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.idf.operationservice.domain.entity.ExchangeRate;
import com.idf.operationservice.exception.CurrencyNotFoundException;
import com.idf.operationservice.exception.CurrentRateNotFoundException;
import com.idf.operationservice.repository.ExchangeRateRepository;
import com.idf.operationservice.service.impl.CurrencyConversionServiceImpl;
import com.idf.operationservice.util.ConstantUtil;
import com.idf.operationservice.util.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceImplTest {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    @InjectMocks
    private CurrencyConversionServiceImpl currencyConversionService;

    @Test
    @DisplayName("Concerting to USD successfully")
    void convertToUSDSuccessfully() {
        ExchangeRate exchangeRate = ExchangeRate.builder().currencyPair(ConstantUtil.KZT_USD_PAIR).rate(ConstantUtil.KZT_USD_VALUE_CLOSE)
                .previousCloseRate(ConstantUtil.KZT_USD_VALUE_CLOSE).build();

      when(exchangeRateRepository.findByCurrencyPairAndLocalDate(ConstantUtil.KZT_USD_PAIR, ConstantUtil.TRANSACTION_DATE))
            .thenReturn(Optional.ofNullable(exchangeRate));

        BigDecimal result = currencyConversionService
            .convertToUSD(ConstantUtil.TRANSACTION_SUM_1, ConstantUtil.KZT, ConstantUtil.TRANSACTION_DATE);

        assertNotNull(result);
        assertEquals(ConstantUtil.KZT_TRANSACTION_IN_USD, result);

        verify(exchangeRateRepository).findByCurrencyPairAndLocalDate(ConstantUtil.KZT_USD_PAIR, ConstantUtil.TRANSACTION_DATE);
    }

    @Test
    @DisplayName("Currency rate nor found with CurrentRateNotFoundException")
    void convertToUSDCurrentRateNotFoundException() {
        when(exchangeRateRepository.findByCurrencyPairAndLocalDate(ConstantUtil.EUR_USD_PAIR, ConstantUtil.TRANSACTION_DATE))
            .thenReturn(Optional.empty());

        CurrentRateNotFoundException exception = assertThrows(
            CurrentRateNotFoundException.class, () -> currencyConversionService
                .convertToUSD(ConstantUtil.TRANSACTION_SUM_2, ConstantUtil.EUR, ConstantUtil.TRANSACTION_DATE)
        );

        assertEquals(ExceptionMessage.CURRENT_RATE_NOT_FOUND.getDescription(), exception.getMessage());

        verify(exchangeRateRepository).findByCurrencyPairAndLocalDate(ConstantUtil.EUR_USD_PAIR, ConstantUtil.TRANSACTION_DATE);
    }

    @Test
    @DisplayName("Invalid currency pair converted with CurrencyNotFoundException")
    void convertToUSDInvalidPairCurrencyNotFound() {
        CurrencyNotFoundException exception = assertThrows(
            CurrencyNotFoundException.class, () -> currencyConversionService
                .convertToUSD(ConstantUtil.TRANSACTION_SUM_1, ConstantUtil.INVALID_PAIR, ConstantUtil.TRANSACTION_DATE)
        );

        assertEquals(ExceptionMessage.CURRENCY_NOT_FOUND.getDescription(), exception.getMessage());

        verifyNoInteractions(exchangeRateRepository);
    }
}
