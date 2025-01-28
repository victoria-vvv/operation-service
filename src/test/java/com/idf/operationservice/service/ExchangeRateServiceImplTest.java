package com.idf.operationservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.idf.operationservice.domain.dto.response.ExchangeRateResponse;
import com.idf.operationservice.domain.entity.ExchangeRate;
import com.idf.operationservice.exception.InvalidDateFormatException;
import com.idf.operationservice.mapper.ExchangeRateMapper;
import com.idf.operationservice.repository.ExchangeRateRepository;
import com.idf.operationservice.service.client.ExchangeRateFeignClient;
import com.idf.operationservice.service.impl.ExchangeRateServiceImpl;
import com.idf.operationservice.util.ConstantUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceImplTest {

    @Mock
    private ExchangeRateFeignClient exchangeRateFeignClient;
    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    @Mock
    private ExchangeRateMapper exchangeRateMapper;
    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Test
    @DisplayName("Exchange rate saved successfully")
    void testSaveExchangeRateSuccessfully() {
        ExchangeRateResponse exchangeRateResponse = ExchangeRateResponse.builder()
            .symbol(ConstantUtil.RUB_USD_PAIR).date(LocalDate.now()).exchangeRate(ConstantUtil.RUB_USD_VALUE_CLOSE)
                    .previousCloseRate(ConstantUtil.RUB_USD_VALUE_PREVIOUS_CLOSE).build();

        when(exchangeRateMapper.mapToExchangeRate(any(ExchangeRateResponse.class)))
            .thenReturn(new ExchangeRate());
        when(exchangeRateRepository.save(any())).thenReturn(any(ExchangeRate.class));

        exchangeRateService.saveExchangeRate(exchangeRateResponse);

        verify(exchangeRateRepository, times(1)).save(any(ExchangeRate.class));
    }

    @Test
    @DisplayName("Parse date with InvalidDateFormatException")
    void testParseDateInvalidFormat() throws Exception {

        Method method = ExchangeRateServiceImpl.class.getDeclaredMethod(ConstantUtil.PARSE_DATE_METHOD, String.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(exchangeRateService, ConstantUtil.INVALID_DATE);
        });

        assertTrue(exception.getCause() instanceof InvalidDateFormatException);
    }
}