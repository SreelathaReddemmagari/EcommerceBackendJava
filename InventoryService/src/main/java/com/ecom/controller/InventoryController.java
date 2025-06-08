package com.ecom.controller;

import com.ecom.model.Inventory;
import com.ecom.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
//    @GetMapping("/in-stock")
//    public ResponseEntity<Boolean> isInStock(
//            @RequestParam String skuCode,
//            @RequestParam Integer quantity) {
//        boolean isInStock = inventoryService.isInStock(skuCode, quantity);
//        return ResponseEntity.ok(isInStock);
//    }
//    // Check if item is in stock
//
//    // Add stock to an inventory item
//    @PostMapping("/addstock")
//    public ResponseEntity<Inventory> addStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
//        Inventory updatedInventory = inventoryService.addStock(skuCode, quantity);
//        return ResponseEntity.ok(updatedInventory);
//    }
//
//    // Reduce stock from an inventory item
//    @PostMapping(value = "/reducestock",produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Boolean> reduceStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
//        boolean success = inventoryService.reduceStock(skuCode, quantity);
//        return ResponseEntity.ok(success); // Always returns HTTP 200 with true/false as JSON
//    }
//
//    // Get available quantity for a SKU
//    @GetMapping("/available-quantity")
//    public ResponseEntity<Integer> getAvailableQuantity(@RequestParam String skuCode) {
//        int quantity = inventoryService.getAvailableQuantity(skuCode);
//        return ResponseEntity.ok(quantity);
//    }
//
//    // Get inventory details by SKU
//    @GetMapping("/details")
//    public ResponseEntity<Inventory> getInventoryDetails(@RequestParam String skuCode) {
//        Optional<Inventory> inventory = inventoryService.getInventoryBySkuCode(skuCode);
//        return inventory.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }



    // Get inventory by product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable String productId) {
        return inventoryService.getInventoryByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get available stock (excluding reserved)
    @GetMapping("/available/{productId}")
    public ResponseEntity<Integer> getAvailableStock(@PathVariable String productId) {
        try {
            int available = inventoryService.getAvailableStock(productId);
            return ResponseEntity.ok(available);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(0);
        }
    }

    // Create or update inventory
    @PostMapping
    public ResponseEntity<Inventory> saveInventory(@RequestBody Inventory inventory) {
        Inventory saved = inventoryService.saveInventory(inventory);
        return ResponseEntity.ok(saved);
    }

    // Reserve stock for a cart
    @PostMapping(value = "/reserve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> reserveInventory(
            @RequestParam String productId,
            @RequestParam String cartId,
            @RequestParam int quantity) {
        try {
            inventoryService.reserveStock(productId, cartId, quantity);
            return ResponseEntity.ok(true); // Success as boolean
        } catch (RuntimeException e) {
            return ResponseEntity.ok(false); // Failure as boolean
        }
    }


    // Release all reservations for a cart
    @DeleteMapping("/release")
    public ResponseEntity<String> releaseReservationsForCart(@RequestParam String cartId) {
        inventoryService.releaseReservation(cartId);
        return ResponseEntity.ok("Reservations released for cart ID: " + cartId);
    }

    // Reduce stock permanently (order confirmed)
    @PostMapping("/reduce")
    public ResponseEntity<String> reduceStock(
            @RequestParam String productId,
            @RequestParam int quantity) {
        try {
            inventoryService.reduceStock(productId, quantity);
            return ResponseEntity.ok("Stock reduced successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/increase")
    public ResponseEntity<String> increaseStock(
            @RequestParam String productId,
            @RequestParam int quantity) {
        try {
            inventoryService.increaseStock(productId, quantity);
            return ResponseEntity.ok("Stock increased successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
