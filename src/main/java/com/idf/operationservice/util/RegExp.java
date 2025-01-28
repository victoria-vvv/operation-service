package com.idf.operationservice.util;

import lombok.experimental.UtilityClass;
import java.util.regex.Pattern;

/**
 * Класс, содерджащий регулярные выражения для валидации данных.
 */
@UtilityClass
public class RegExp{

    /**
     * Регулярное выражение для проверки формата данных о банковском счете.
     * Он должен быть целочисленным и содержать 10 знаков.
     */
    public final static String accountFormat = "^\\d{10}$";

    /**
     * Регулярное выражение для формата названия валюты.
     * Оно должно содержать только большие латинские буквы.
     */
    public final static String currencyNameFormat = "^\\[A-Z]+$";

    /**
     * Регулярное выражение для формата названия валютной пары.
     * Оно должно содержать только большие латинские буквы, разделенные знаком /.
     */
    public final static String currencyPairFormat = "^[A-Z]+(\\/([A-Z]+))*$";

    /**
     * Регулярное выражение для формата стоимости валюты.
     * Она должно содержать число с 4 знаками после запятой.
     */
    public final static String currencyValueFormat = "^\\d+(\\.\\d{4})?$";

    /**
     * Регулярное выражение для формата суммы транзакции.
     * Она должна содержать число с 2 знаками после запятой
     */
    public final static String expenseFormat = "^\\d+(\\.\\d{2})?$";

    /**
     * Паттерн для поиска валютной пары по входящей валюте
     */
    public final static Pattern CURRENCY_CODE_PATTERN = Pattern.compile("^[A-Z]+$");
}
