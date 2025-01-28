package com.idf.operationservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс, содержащий сообщения об ошибках.
 */
@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    LAST_RATE_NOT_FOUND("Данные о предыдущем курсе не найдены"),
    CURRENT_RATE_NOT_FOUND("Не удалось получить данные о текущем курсе валют"),
    CURRENCY_NOT_FOUND("Валюта операции не устанвлена"),
    BAD_REQUEST_EXCEPTION("Запрос не может быть обработан"),
    INVALID_DATE_TIME_FORMAT("Invalid date format: %s");

    private final String description;

}