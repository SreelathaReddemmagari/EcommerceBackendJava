package com.ecom.client;

import com.ecom.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "InventoryService",url = "http://localhost:9095")
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

}
