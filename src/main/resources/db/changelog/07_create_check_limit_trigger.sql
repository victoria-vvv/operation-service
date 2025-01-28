CREATE TRIGGER check_transaction_limits
    BEFORE INSERT ON transaction_limit
    FOR EACH ROW
EXECUTE FUNCTION set_default_limit_value();