package com.ecom.controller;

import com.ecom.model.Inventory;
import com.ecom.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    // Check if item is in stock

    // Add stock to an inventory item
    @PostMapping("/addstock")
    public ResponseEntity<Inventory> addStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        Inventory updatedInventory = inventoryService.addStock(skuCode, quantity);
        return ResponseEntity.ok(updatedInventory);
    }

    // Reduce stock from an inventory item
    @PostMapping(value = "/reducestock",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> reduceStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        boolean success = inventoryService.reduceStock(skuCode, quantity);
        return ResponseEntity.ok(success); // Always returns HTTP 200 with true/false as JSON
    }

    // Get available quantity for a SKU
    @GetMapping("/available-quantity")
    public ResponseEntity<Integer> getAvailableQuantity(@RequestParam String skuCode) {
        int quantity = inventoryService.getAvailableQuantity(skuCode);
        return ResponseEntity.ok(quantity);
    }

    // Get inventory details by SKU
    @GetMapping("/details")
    public ResponseEntity<Inventory> getInventoryDetails(@RequestParam String skuCode) {
        Optional<Inventory> inventory = inventoryService.getInventoryBySkuCode(skuCode);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
