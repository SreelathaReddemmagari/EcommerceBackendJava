package com.Ecom_microservices.order_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusDTO {
    private int orderStatusId;
    private String statusName;
    private String statusDescription;
    private LocalDateTime statusDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}