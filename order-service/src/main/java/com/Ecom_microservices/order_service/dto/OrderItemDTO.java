package com.Ecom_microservices.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderItemDTO {
    @NotBlank(message = "Product ID cannot be blank")
    private Long UserId;


    private int quantity;
}