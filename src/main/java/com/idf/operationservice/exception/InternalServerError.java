package com.idf.operationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, указывающее на внутреннию ошибку сервера.
 * <p>
 * Возвращает HTTP-ответ с кодом 500 Internal server error.
 * <p>
 * Используется для обозначения ситуаций, когда запрос сообщает о проблеме,
 * котороя может появиться при попытке получить доступ к веб-странице.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends RuntimeException {
    public InternalServerError() {
        super();
    }
    /**
     * Создает новый экземпляр InternalServerError с заданным сообщением.
     *
     * @param message Сообщение об ошибке
     */
    public InternalServerError(String message) {
        super(message);
    }
}
