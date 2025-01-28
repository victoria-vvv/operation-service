CREATE TABLE transaction_limit (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  category VARCHAR(30),
                                  limit_value DECIMAL(16,2) DEFAULT 1000.00,
                                  limit_currency VARCHAR(10) DEFAULT 'USD',
                                  limit_setting_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
