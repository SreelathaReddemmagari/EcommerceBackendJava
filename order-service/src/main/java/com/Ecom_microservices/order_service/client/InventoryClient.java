package com.Ecom_microservices.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
//    @PostMapping("/api/inventory/reduce")
//    boolean reduceStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);
//
//    @PostMapping("/api/inventory/add")
//    void addStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);

    @PostMapping("inventory/reduce")
    String reduceStock(@RequestParam("productId") String productId, @RequestParam("quantity") Integer quantity);


    @PostMapping("inventory/increase")
    String increaseStock(@RequestParam("productId") String productId, @RequestParam("quantity") Integer quantity);


    @DeleteMapping("inventory/release")
    String releaseReservation(@RequestParam("cartId") String cartId);

    @PostMapping("inventory/add")
    boolean addStock(@RequestParam("productId") String productId, @RequestParam("quantity") Integer quantity);
}
