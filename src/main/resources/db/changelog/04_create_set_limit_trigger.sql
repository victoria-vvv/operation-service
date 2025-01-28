CREATE FUNCTION set_default_limit_value()
    RETURNS TRIGGER AS
$limit_check$
BEGIN
    IF NEW.limit_value = 0 THEN
        NEW.limit_value := 1000.00;
    END IF;
    RETURN NEW;
END;
$limit_check$ LANGUAGE plpgsql;
