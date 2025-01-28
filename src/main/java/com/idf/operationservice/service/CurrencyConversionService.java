package com.idf.operationservice.service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Сервис для работы с конверсией валют
 */
public interface CurrencyConversionService {

     /**
      * Метод осуществляет перерасчет заданной суммы в доллары
      */
     BigDecimal convertToUSD(BigDecimal amount, String currency, LocalDate transactionDate);

}
