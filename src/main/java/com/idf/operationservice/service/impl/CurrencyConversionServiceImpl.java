package com.idf.operationservice.service.impl;

import com.idf.operationservice.domain.entity.ExchangeRate;
import com.idf.operationservice.exception.CurrencyNotFoundException;
import com.idf.operationservice.exception.CurrentRateNotFoundException;
import com.idf.operationservice.repository.ExchangeRateRepository;
import com.idf.operationservice.service.CurrencyConversionService;
import com.idf.operationservice.util.ExceptionMessage;
import com.idf.operationservice.util.RegExp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.regex.Matcher;

/**
 * Сервис, реализующий методы для конвертации валюты в USD
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final String USD_CURRENCY = "USD";
    private final String CURRENCY_TO_USD = "/USD";

    /**
     * Конвертирует сумму транзакции в доллары (USD).
     *
     * @param amount сумма транзакции
     * @param currency валюта транзакции
     * @param transactionDate дата транзакции
     * @return сумма в долларах
     */
    public BigDecimal convertToUSD(BigDecimal amount, String currency, LocalDate transactionDate) {
        if (currency.equals(USD_CURRENCY)) {
            return amount;
        }

        String currencyPair = getCurrencyPairForConversion(currency);

        BigDecimal exchangeRate = exchangeRateRepository
            .findByCurrencyPairAndLocalDate(currencyPair, transactionDate)
            .map(ExchangeRate::getRate)
            .orElseThrow(() -> new CurrentRateNotFoundException(ExceptionMessage.CURRENT_RATE_NOT_FOUND.getDescription()));

        BigDecimal amount1 = amount.setScale(4, RoundingMode.HALF_UP);
        return amount1.divide(exchangeRate, 2, RoundingMode.HALF_UP);
    }

    /**
     * Возвращает валютную пару для перевода в USD.
     */
    private String getCurrencyPairForConversion(String currency) {
        Matcher matcher = RegExp.CURRENCY_CODE_PATTERN.matcher(currency);
        if (matcher.matches()) {
            return currency + CURRENCY_TO_USD;
        } else throw new CurrencyNotFoundException(ExceptionMessage.CURRENCY_NOT_FOUND.getDescription());
    }
}
