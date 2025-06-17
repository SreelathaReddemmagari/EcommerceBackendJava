package com.Ecom_microservices.order_service.service;

import com.Ecom_microservices.order_service.dto.OrderPaymentUpdateDTO;
import com.Ecom_microservices.order_service.dto.OrderRequestDTO;
import com.Ecom_microservices.order_service.dto.OrderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    void cancelOrder(String orderId, String reason);
     OrderResponseDTO getOrderById(String orderId);
     List<OrderResponseDTO> getOrdersByUserId(String userId);

    OrderResponseDTO updatePaymentDetails(OrderPaymentUpdateDTO paymentUpdateDTO);
}