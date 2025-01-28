package com.idf.operationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, указывающее на ошибку, вызванную некорректным форматом переданной даты.
 * <p>
 * Возвращает HTTP-ответ с кодом 400 Bad Request.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateFormatException extends RuntimeException{
    public InvalidDateFormatException() {
        super();
    }

    /**
     * Создает новый объект исключения с заданным сообщением.
     *
     * @param message сообщение об ошибке
     */
    public InvalidDateFormatException(String message) {
        super(message);
    }
}
