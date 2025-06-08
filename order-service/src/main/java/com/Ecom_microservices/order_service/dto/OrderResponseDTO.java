package com.Ecom_microservices.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private String orderId;
    private Long userId;
    private String paymentId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemResponseDTO> orderItems;
    private List<OrderStatusDTO> orderStatuses;
}