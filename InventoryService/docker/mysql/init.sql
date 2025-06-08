CREATE DATABASE IF NOT EXISTS `inventory_service`;

USE `inventory_service`;

CREATE TABLE IF NOT EXISTS inventory (
    inventory_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(100) NOT NULL,
    sku VARCHAR(100) NOT NULL UNIQUE,
    quantity INT NOT NULL,
    reserved_quantity INT NOT NULL DEFAULT 0,
    reorder_level INT NOT NULL DEFAULT 10,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    status_description TEXT
);


