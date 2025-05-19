CREATE DATABASE IF NOT EXISTS `order-service`;
USE `order-service`;
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_number VARCHAR(255),
    sku_code VARCHAR(255),
    order_date DATETIME,
    price DECIMAL(19, 2),
    quantity INT
);
