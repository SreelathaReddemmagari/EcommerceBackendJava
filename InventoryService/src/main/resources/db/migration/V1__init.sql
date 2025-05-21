USE `inventory-service`;

CREATE TABLE inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    skuCode VARCHAR(255) NOT NULL UNIQUE,
   sku_code VARCHAR(255) NOT NULL UNIQUE,
    quantity INT NOT NULL
);