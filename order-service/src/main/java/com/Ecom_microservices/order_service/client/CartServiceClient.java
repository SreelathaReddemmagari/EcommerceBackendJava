package com.Ecom_microservices.order_service.client;

import com.Ecom_microservices.order_service.dto.CartItemDTO;
//import com.ecom.config.FeignClientConfig;
import com.Ecom_microservices.order_service.config.FeignClientConfig;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cart-service",configuration = FeignClientConfig.class)
public interface CartServiceClient {
    @GetMapping("/api/cart/{userId}/items") // cart service endpoint
    List<CartItemDTO> getCartItems(@PathVariable("userId") Long userId);

    @DeleteMapping("api/cart/{userId}")
    void clearCart(@PathVariable("userId") Long userId);
}
