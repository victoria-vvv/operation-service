package com.idf.operationservice.service.impl;

import com.idf.operationservice.exception.InvalidDateFormatException;
import com.idf.operationservice.service.client.ExchangeRateFeignClient;
import com.idf.operationservice.domain.entity.ExchangeRate;
import com.idf.operationservice.domain.dto.response.ExchangeRateResponse;
import com.idf.operationservice.exception.CurrentRateNotFoundException;
import com.idf.operationservice.exception.LastRateNotFoundException;
import com.idf.operationservice.mapper.ExchangeRateMapper;
import com.idf.operationservice.repository.ExchangeRateRepository;
import com.idf.operationservice.service.ExchangeRateService;
import com.idf.operationservice.util.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис, реализующий методы для работы с котировками курсов валют
 */
@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateFeignClient exchangeRateFeignClient;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateMapper exchangeRateMapper;

    @Value("api.key")
    private String apiKey;
    private final String REQUEST_PERIOD = "1day";
    private final String KZT_USD = "KZT_USD";
    private final String RUB_USD = "RUB_USD";
    private final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private final String RESPONSE_VALUES = "values";
    private final String DATETIME_KEY = "datetime";
    private final String CLOSE_KEY = "close";


    /**
     * Метод инициирует отправку запроса для получения котировок ежедневно в 11 утра
     */
    @Scheduled(cron = "0 0 11 * * ?")
    void launchUpdatingExchangeRates() {
        fetchAndSaveExchangeRates(KZT_USD);
        fetchAndSaveExchangeRates(RUB_USD);
    }

    /**
     * Метод, аккумулирующий работу получения и сохранени котировок курсов валют
     */
    private void fetchAndSaveExchangeRates(String symbol) {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        BigDecimal lastExchangeRate =
            exchangeRateRepository.findByCurrencyPairAndLocalDate(symbol, yesterday)
                .map(ExchangeRate::getRate)
                .orElseThrow(() -> new LastRateNotFoundException(ExceptionMessage.LAST_RATE_NOT_FOUND.getDescription()));

        List<Map<String, String>> values = receiveValues(symbol);

        ExchangeRateResponse response = mapValues(values, symbol, lastExchangeRate);

        saveExchangeRate(response);
    }

    /**
     * Метод, отправляющий запрос для получения данных о котировках
     */
    private List<Map<String, String>> receiveValues(String symbol) {
        ResponseEntity<Map<String, Object>> response =
            exchangeRateFeignClient.getExchangeRates(symbol, REQUEST_PERIOD, apiKey);

        Map<String, Object> data = Optional.ofNullable(response.getBody())
            .filter(d -> d.containsKey(RESPONSE_VALUES))
            .orElseThrow(() -> new CurrentRateNotFoundException(ExceptionMessage.CURRENT_RATE_NOT_FOUND.getDescription()));

        List<Map<String, String>> values = (List<Map<String, String>>) data.get(RESPONSE_VALUES);

        if (values.isEmpty()) {
            throw new CurrentRateNotFoundException(ExceptionMessage.CURRENT_RATE_NOT_FOUND.getDescription());
        }
        return values;
    }

    /**
     * Метод преобразует полученные данные о котировках курсов ввалют и маппит их в поля
     * хранимого объекта в базе данных
     */
    private ExchangeRateResponse mapValues(List<Map<String, String>> values, String symbol, BigDecimal lastExchangeRate) {
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        for (Map<String, String> value : values) {
            String datetime = value.get(DATETIME_KEY);
            LocalDate date = parseDate(datetime);

            BigDecimal closeRate = new BigDecimal(value.get(CLOSE_KEY));

            exchangeRateResponse.setSymbol(symbol);
            exchangeRateResponse.setDate(date);
            exchangeRateResponse.setExchangeRate(closeRate);
            exchangeRateResponse.setPreviousCloseRate(lastExchangeRate);
        }
        return exchangeRateResponse;
    }

    /**
     * Метод парсит данные о дате установления котировки в формат хранения в бд
     */
    private LocalDate parseDate(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            return LocalDate.parse(datetime, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(ExceptionMessage.INVALID_DATE_TIME_FORMAT.getDescription() + datetime);
        }
    }

    /**
     * Метод сохраняет полученные данные котировок курсов валют в базу данных
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveExchangeRate(ExchangeRateResponse response) {
        ExchangeRate exchangeRate = exchangeRateMapper.mapToExchangeRate(response);

        exchangeRateRepository.save(exchangeRate);
    }
}
