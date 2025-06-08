package com.Ecom_microservices.order_service.client;

import com.Ecom_microservices.order_service.dto.CartItemDTO;
import com.ecom.config.FeignClientConfig;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cart-service", url = "http://localhost:8084",configuration = FeignClientConfig.class)
public interface CartServiceClient {
    @GetMapping("/api/cart/{userId}/items") // Your cart service endpoint
    List<CartItemDTO> getCartItems(@PathVariable("userId") Long userId);

    @DeleteMapping("api/cart/{userId}") // Add the HTTP method and path
    void clearCart(@PathVariable("userId") Long userId);
}
