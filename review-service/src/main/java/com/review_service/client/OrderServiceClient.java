package com.review_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping("/api/orders/user/{userId}/product/{productId}/exists")
    boolean hasPurchased(@PathVariable Long userId, @PathVariable Long productId);
}

