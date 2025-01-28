package com.idf.operationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое при отсутствии запрашиваемых данных о текущем курсе валют.
 * <p>
 * Это подкласс RuntimeException и может использоваться для обработки ситуаций, когда не найдены данные по запросу.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrentRateNotFoundException extends RuntimeException {
    public CurrentRateNotFoundException() {
        super();
    }

    /**
     * Создает новый объект исключения с заданным сообщением.
     *
     * @param message сообщение об ошибке
     */
    public CurrentRateNotFoundException(String message) {
        super(message);
    }
}
