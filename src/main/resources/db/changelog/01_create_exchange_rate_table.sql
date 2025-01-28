CREATE TABLE exchange_rate (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              currency_pair VARCHAR(255) NOT NULL,
                              date DATE DEFAULT CURRENT_DATE,
                              rate DECIMAL (10, 4),
                              previous_close_rate DECIMAL (10,4)
);