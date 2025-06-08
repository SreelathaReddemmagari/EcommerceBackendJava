package com.Ecom_microservices.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String id;
    private String name;
    private BigDecimal price;
    private BigDecimal discountedPrice;
}