package com.Ecom_microservices.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDTO {
    private int orderItemId;
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}