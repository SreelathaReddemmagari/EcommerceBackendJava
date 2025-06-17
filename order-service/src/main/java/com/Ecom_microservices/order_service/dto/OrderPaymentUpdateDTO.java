package com.Ecom_microservices.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentUpdateDTO {
    private String orderId;
    private String paymentId;
    private String status;
}
