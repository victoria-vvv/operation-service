INSERT INTO exchange_rate (currency_pair, date, rate, previous_close_rate)
VALUES ('KZT/USD', '2025-01-27 00:00:00+06', 519.6365, 520.6368),
       ('RUB/USD', '2025-01-27 00:00:00+06', 97.1617, 10.0255),
       ('KZT/USD', '2025-01-01 00:00:00+06', 519.6362, 518.6365),
       ('RUB/USD', '2025-01-01 00:00:00+06', 96.1617, 98.1617),
       ('KZT/USD', '2025-01-02 00:00:00+06', 519.6365, 519.6365),
       ('RUB/USD', '2025-01-02 00:00:00+06', 99.1617, 99.1617),
       ('KZT/USD', '2025-01-03 00:00:00+06', 517.6365, 517.6365),
       ('RUB/USD', '2025-01-03 00:00:00+06', 97.1117, 97.1237);

INSERT INTO transaction_limit (category, limit_value, limit_setting_time)
VALUES ('PRODUCT', 1000.00, '2025-01-01 07:00:00+06'),
       ('SERVICE', 500.00, '2025-01-01 07:00:00+06'),
       ('PRODUCT', 7000.00, '2025-01-27 18:04:09+00'),
       ('SERVICE', 700.00, '2025-01-27 18:04:09+00');

INSERT INTO expense_transaction (account_from, account_to, transaction_sum, currency, transaction_time, category,
                                 rest_limit_sum, limit_exceeded)
VALUES (1034567812, 1022456789, 200.25, 'RUB', '2025-01-27 17:56:22+00', 'PRODUCT', 995.88, FALSE),
       (1034567812, 1022456789, 350.00, 'USD', '2025-01-27 17:59:01+00', 'SERVICE', 150.00, FALSE),
       (1034567813, 1022456789, 50000.00, 'RUB', '2025-01-27 18:00:02+00', 'PRODUCT', 481.27, FALSE),
       (1034567814, 1022456789, 53567.00, 'KZT', '2025-01-27 18:02:53+00', 'SERVICE', 46.91, FALSE),
       (1034567814, 1022456789, 13500.80, 'RUB', '2025-01-27 18:02:53+00', 'SERVICE', -92.04, TRUE),
       (1034567814, 1022456789, 556789.99, 'KZT', '2025-01-27 18:02:53+00', 'PRODUCT', -5249.28, TRUE),
       (1034567814, 1022456789, 589.18, 'KZT', '2025-01-27 18:09:09+00', 'PRODUCT', 749.59, FALSE),
       (1034567814, 1022456789, 1300.80, 'RUB', '2025-01-27 18:10:46+00', 'SERVICE', 94.57, FALSE),
       (1034567814, 1022456789, 589.18, 'USD', '2025-01-27 18:12:08+00', 'PRODUCT', -101.00, TRUE),
       (1034567814, 1022456789, 1300.80, 'USD', '2025-01-27 18:12:08+00', 'SERVICE', -1, TRUE);

