package com.idf.operationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое при отсутствии запрашиваемых данных о последнем курсе валют.
 * <p>
 * Это подкласс RuntimeException и может использоваться для обработки ситуаций, когда не найдены данные по запросу.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class LastRateNotFoundException extends RuntimeException {
        public LastRateNotFoundException() {
            super();
        }

    /**
     * Создает новый объект исключения с заданным сообщением.
     *
     * @param message сообщение об ошибке
     */
        public LastRateNotFoundException(String message) {
            super(message);
        }
    }
