package com.idf.operationservice.util;

import lombok.experimental.UtilityClass;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@UtilityClass
public class ConstantUtil {

    public final long ACCOUNT_FROM = Long.valueOf(1034567814);
    public final long ACCOUNT_TO = Long.valueOf(1022456789);

    public final double PRODUCT_SUM = 608168.60;
    public final double SERVICE_SUM = 70019.40;

    public final String KZT = "KZT";
    public final String USD = "USD";
    public final String RUB = "RUB";
    public final String EUR = "EUR";
    public final String KZT_USD_PAIR = "KZT/USD";
    public final String RUB_USD_PAIR = "RUB/USD";
    public final String EUR_USD_PAIR = "EUR/USD";
    public final String INVALID_PAIR = "RUB/RUB";
    public final String PRODUCT_CATEGORY = "PRODUCT";
    public final String SERVICE_CATEGORY = "SERVICE";
    public final String LIMIT_KEY_PREFIX = "limit:";
    public final String SUM_KEY_PREFIX = "sum:";
    public final String ADD_LIMIT_URL = "/transaction-limit/add-limit";
    public final String FIND_LIMITS_URL = "/transaction-limit/exceeding-limit";
    public final String TRANSACTION_URL = "/api/transactions/expense-transaction";
    public final String INVALID_DATE = "invalid-date";
    public final String PARSE_DATE_METHOD = "parseDate";

    public final BigDecimal KZT_USD_VALUE_CLOSE = new BigDecimal("519.6365");
    public final BigDecimal KZT_TRANSACTION_IN_USD = new BigDecimal("1071.50");
    public final BigDecimal RUB_USD_VALUE_CLOSE = new BigDecimal("97.1617");
    public final BigDecimal RUB_USD_VALUE_PREVIOUS_CLOSE = new BigDecimal("100.0255");
    public final BigDecimal PRODUCT_LIMIT_1 = new BigDecimal("1000.00");
    public final BigDecimal SERVICE_LIMIT_1 = new BigDecimal("500.00");
    public final BigDecimal PRODUCT_LIMIT_2 = new BigDecimal("7000.00");
    public final BigDecimal SERVICE_LIMIT_2 = new BigDecimal("700.00");
    public final BigDecimal LAST_LIMIT_VALUE = new BigDecimal("1000.00");
    public final BigDecimal TRANSACTION_SUM_1 = new BigDecimal("556789.99");
    public final BigDecimal TRANSACTION_SUM_2 = new BigDecimal("13500.80");
    public final BigDecimal TRANSACTION_SUM_3 = new BigDecimal("589.18");
    public final BigDecimal TRANSACTION_SUM_4 = new BigDecimal("1300.80");
    public final BigDecimal REST_LIMIT_1 = new BigDecimal("-5249.28");
    public final BigDecimal REST_LIMIT_2 = new BigDecimal("-92.04");
    public final BigDecimal REST_LIMIT_3 = new BigDecimal("-101.00");
    public final BigDecimal REST_LIMIT_4 = new BigDecimal("-1.00");
    public final BigDecimal PRODUCT_TOTAL_SUM = new BigDecimal("300");
    public final BigDecimal SERVICE_TOTAL_SUM = new BigDecimal("200");
    public final BigDecimal EXPECTED_SERVICE_SUM = new BigDecimal("200");
    public static final LocalDate TRANSACTION_DATE = LocalDate.of(2025, 1, 27);

    public final ZonedDateTime LIMIT_SET_TIME_1 =
        OffsetDateTime.parse("2025-01-01T01:00Z").toZonedDateTime();
    public final ZonedDateTime LIMIT_SET_TIME_2 =
        OffsetDateTime.parse("2025-01-27T18:04:09Z").toZonedDateTime();
    public final ZonedDateTime TRANSACTION_TIME_1 =
        OffsetDateTime.parse("2025-01-27T18:02:53Z").toZonedDateTime();
    public final ZonedDateTime TRANSACTION_TIME_2 =
        OffsetDateTime.parse("2025-01-27T18:12:08Z").toZonedDateTime();
}
