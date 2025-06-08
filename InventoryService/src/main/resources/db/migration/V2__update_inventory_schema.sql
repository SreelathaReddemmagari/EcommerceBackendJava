ALTER TABLE inventory
DROP COLUMN reserved_quantity,
DROP COLUMN updated_at;

---- Create new table for reservations
--CREATE TABLE inventory_reservations (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    product_id VARCHAR(255) NOT NULL,
--    cart_id VARCHAR(255) NOT NULL,
--    quantity INT NOT NULL,
--    reserved_until TIMESTAMP NOT NULL
--);