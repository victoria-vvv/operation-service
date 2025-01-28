package com.idf.operationservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

/**
 * Клиент, отправляющий запрос во внешний источник для пролучения данных по последнему курсу
 */
@FeignClient(name = "exchangeRateClient", url = "https://twelvedata.com")
public interface ExchangeRateFeignClient {

    @GetMapping("/")
    ResponseEntity<Map<String, Object>> getExchangeRates(
        @RequestParam("symbol") String symbol,
        @RequestParam("interval") String interval,
        @RequestParam("apikey") String apiKey
    );
}
