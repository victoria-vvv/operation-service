package com.idf.operationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое при отсутствии запрашиваемых данных по данной валюте.
 * <p>
 * Это подкласс RuntimeException и может использоваться для обработки ситуаций, когда не найдены данные по валюте.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException() {
        super();
    }

    /**
     * Создает новый объект исключения с заданным сообщением.
     *
     * @param message сообщение об ошибке
     */
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}