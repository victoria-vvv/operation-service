CREATE TABLE expense_transaction (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    account_from BIGINT,
                                    account_to BIGINT,
                                    transaction_sum DECIMAL(16,2),
                                    currency VARCHAR(10),
                                    transaction_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                                    category VARCHAR(30),
                                    rest_limit_sum DECIMAL(16,2),
                                    limit_exceeded BOOLEAN
);