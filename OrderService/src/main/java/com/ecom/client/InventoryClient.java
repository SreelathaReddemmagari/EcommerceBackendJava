package com.ecom.client;

import com.ecom.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "inventory",url = "http://localhost:8082")
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.GET,value = "/inventory/in-stock")
    boolean isInStock(@RequestParam String skuCode,@RequestParam Integer quantity);
    @PostMapping("/inventory/addstock")
    Inventory addStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    @PostMapping(value = "/inventory/reducestock",produces = MediaType.APPLICATION_JSON_VALUE)
    boolean reduceStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    @GetMapping("/inventory/available-quantity")
    Integer getAvailableQuantity(@RequestParam String skuCode);

    @GetMapping("/inventory/details")
    Inventory getInventoryDetails(@RequestParam String skuCode);
}
