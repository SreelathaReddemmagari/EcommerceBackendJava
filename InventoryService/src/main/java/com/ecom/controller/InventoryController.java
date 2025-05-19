package com.ecom.controller;

import com.ecom.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @GetMapping("/in-stock")
    public ResponseEntity<Boolean> isInStock(
            @RequestParam String skuCode,
            @RequestParam Integer quantity) {
        boolean isInStock = inventoryService.isInStock(skuCode, quantity);
        return ResponseEntity.ok(isInStock);
    }
}
