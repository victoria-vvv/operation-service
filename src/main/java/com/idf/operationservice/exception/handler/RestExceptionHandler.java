package com.idf.operationservice.exception.handler;

import com.idf.operationservice.exception.BadRequestException;
import com.idf.operationservice.exception.CurrencyNotFoundException;
import com.idf.operationservice.exception.CurrentRateNotFoundException;
import com.idf.operationservice.exception.InvalidDateFormatException;
import com.idf.operationservice.exception.LastRateNotFoundException;
import com.idf.operationservice.exception.InternalServerError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Глобальный обработчик исключений для REST-контроллеров.
 * <p>
 * Обрабатывает исключения, возникающие в контроллерах, и возвращает соответствующие ответы клиенту.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Обрабатывает исключение CurrencyNotFoundException и возвращает HTTP-ответ с кодом 404 Not Found.
     *
     * @param e исключение CurrencyNotFoundException
     * @return ответ клиенту с сообщением об ошибке и кодом состояния 404
     */
    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleCurrencyNotFoundException(CurrencyNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponseDto(e.getMessage()));
    }

    /**
     * Обрабатывает исключение CurrentRateNotFoundException и возвращает HTTP-ответ с кодом 404 Not Found.
     *
     * @param e исключение CurrentRateNotFoundException
     * @return ответ клиенту с сообщением об ошибке и кодом состояния 404
     */
    @ExceptionHandler(CurrentRateNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleCurrentRateNotFoundException(CurrentRateNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponseDto(e.getMessage()));
    }

    /**
     * Обрабатывает исключение LastRateNotFoundException и возвращает HTTP-ответ с кодом 404 Not Found.
     *
     * @param e исключение LastRateNotFoundException
     * @return ответ клиенту с сообщением об ошибке и кодом состояния 404
     */
    @ExceptionHandler(LastRateNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleLastRateNotFoundException(LastRateNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponseDto(e.getMessage()));
    }

    /**
     * Обрабатывает исключение BadRequestException и возвращает HTTP-ответ с кодом 400 Bad Request.
     *
     * @param e исключение BadRequestException
     * @return ответ клиенту с сообщением об ошибке и кодом состояния 400
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponseDto> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getMessage()));
    }

    /**
     * Обрабатывает исключение InternalServerError и возвращает HTTP-ответ с кодом 500 Internal Server Error.
     *
     * @param e исключение InternalServerError
     * @return ответ клиенту с сообщением об ошибке и кодом состояния 500
     */
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ExceptionResponseDto> handleInternalServerError(InternalServerError e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponseDto(e.getMessage()));
    }

    /**
     * Обрабатывает исключение InvalidDateFormatException и возвращает HTTP-ответ с кодом 400 Not Found.
     *
     * @param e исключение InvalidDateFormatException
     * @return ответ клиенту с сообщением об ошибке и кодом состояния 400
     */
    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<ExceptionResponseDto> handleInvalidDateFormatException(InvalidDateFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDto(e.getMessage()));
    }
}