package com.Ecom_microservices.order_service.utility;

import com.Ecom_microservices.order_service.entity.CombinedOrderEntity;
import com.Ecom_microservices.order_service.entity.Orders;

import java.time.LocalDateTime;

public class OrderMapper {
    public static CombinedOrderEntity toCombined(OrderWrapper wrapper) {
        Orders order = wrapper.getOrder();

        CombinedOrderEntity combined = new CombinedOrderEntity();
        combined.setPk("o#" + order.getId());
        combined.setSk("order#" + order.getId());

        combined.setId(order.getId());
        combined.setUserId(order.getUserId());
        combined.setPrimaryProductId(order.getPrimaryProductId());
        combined.setPaymentId(order.getPaymentId());
        combined.setOrderDate(order.getOrderDate().toString());
        combined.setTotalPrice(order.getTotalPrice());
        combined.setCreatedAt(order.getCreatedAt().toString());
        combined.setUpdatedAt(order.getUpdatedAt().toString());
        combined.setStatus(order.getStatus());

        combined.setOrderItems(wrapper.getOrderItems());
        combined.setStatusHistory(wrapper.getStatusHistory());

        return combined;
    }

    public static OrderWrapper fromCombined(CombinedOrderEntity combined) {
        Orders order = new Orders();
        order.setId(combined.getId());
        order.setUserId(combined.getUserId());
        order.setPrimaryProductId(combined.getPrimaryProductId());
        order.setPaymentId(combined.getPaymentId());
        order.setOrderDate(LocalDateTime.parse(combined.getOrderDate()));
        order.setTotalPrice(combined.getTotalPrice());
        order.setCreatedAt(LocalDateTime.parse(combined.getCreatedAt()));
        order.setUpdatedAt(LocalDateTime.parse(combined.getUpdatedAt()));
        order.setStatus(combined.getStatus());

        OrderWrapper wrapper = new OrderWrapper();
        wrapper.setOrder(order);
        wrapper.setOrderItems(combined.getOrderItems());
        wrapper.setStatusHistory(combined.getStatusHistory());

        return wrapper;
    }
}
