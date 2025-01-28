package com.idf.operationservice.service;

import com.idf.operationservice.domain.dto.response.ExchangeRateResponse;

/**
 * Сервис для работы с курсами валют
 */
public interface ExchangeRateService {

  /**
   * Метод сохраняет данные о котировках
   */
  void saveExchangeRate(ExchangeRateResponse exchangeRateResponse);
}
