package com.idf.operationservice.exception.handler;

/**
 * DTO для представления сообщения об ошибке.
 * <p>
 * Используется для передачи сообщений об ошибках от сервисов или контроллеров.
 */
public record ExceptionResponseDto(String errorMessage) {
}
