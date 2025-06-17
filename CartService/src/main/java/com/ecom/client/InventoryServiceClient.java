package com.ecom.client;

import com.ecom.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
//inventory service client which is used to interact with the inventory service  to reserve,reduce and release stocks
@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {

    @GetMapping("/inventory/product/{productId}")
    InventoryDto getInventoryByProductId(@PathVariable("productId") String productId);

    @PostMapping("/inventory/reduce")
    boolean reduceStock(@RequestParam("productId") String productId,
                        @RequestParam("quantity") Integer quantity);

    // New API: Reserve inventory for a cart
    @PostMapping("/inventory/reserve")
    boolean reserveInventory(@RequestParam("cartId") String cartId,
                             @RequestParam("productId") String productId,
                             @RequestParam("quantity") Integer quantity);

    // New API: Release all reservations for a cart (e.g. when cart is cleared)
    @DeleteMapping("/inventory/release")
    void releaseReservationsForCart(@RequestParam("cartId") String cartId);

    @GetMapping("/available/{productId}")
    Integer getAvailableStock(@PathVariable("productId") String productId);
}
