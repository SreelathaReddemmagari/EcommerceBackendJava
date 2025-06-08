package com.ecom.service;

import com.ecom.model.Inventory;
import com.ecom.model.InventoryReservation;
import com.ecom.repository.InventoryRepository;
import com.ecom.repository.InventoryReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
//    @Autowired
//    private InventoryRepository inventoryRepository;
//    //find an inventory using sku code where quantity is >=0
//    public boolean isInStock(String skuCode,Integer quantity){
//        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode,quantity);
//
//    }
//    public Inventory addStock(String skuCode, Integer quantityToAdd) {
//        Optional<Inventory> inventoryOpt = inventoryRepository.findBySkuCode(skuCode);
//        Inventory inventory;
//        if (inventoryOpt.isPresent()) {
//            inventory = inventoryOpt.get();
//            inventory.setQuantity(inventory.getQuantity() + quantityToAdd);
//        } else {
//            inventory = new Inventory();
//            inventory.setSkuCode(skuCode);
//            inventory.setQuantity(quantityToAdd);
//        }
//        return inventoryRepository.save(inventory);
//    }
//
//    // Reduce stock quantity for an SKU if enough stock is available
//    public boolean reduceStock(String skuCode, Integer quantityToReduce) {
//        Optional<Inventory> inventoryOpt = inventoryRepository.findBySkuCode(skuCode);
//        if (inventoryOpt.isPresent()) {
//            Inventory inventory = inventoryOpt.get();
//            if (inventory.getQuantity() >= quantityToReduce) {
//                inventory.setQuantity(inventory.getQuantity() - quantityToReduce);
//                inventoryRepository.save(inventory);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // Get total quantity available for a given SKU
//    public Integer getAvailableQuantity(String skuCode) {
//        return inventoryRepository.findBySkuCode(skuCode)
//                .map(Inventory::getQuantity)
//                .orElse(0);
//    }
//
//    // Fetch inventory details by SKU code
//    public Optional<Inventory> getInventoryBySkuCode(String skuCode) {
//        return inventoryRepository.findBySkuCode(skuCode);
//    }

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryReservationRepository reservationRepository;

    // Basic CRUD omitted for brevity
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Optional<Inventory> getInventoryByProductId(String productId) {
        return inventoryRepository.findByProductId(productId);
    }

    // Calculate available stock dynamically
    public int getAvailableStock(String productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        LocalDateTime now = LocalDateTime.now();
        List<InventoryReservation> activeReservations = reservationRepository.findByProductIdAndReservedUntilAfter(productId, now);

        int reserved = activeReservations.stream()
                .mapToInt(InventoryReservation::getQuantity)
                .sum();

        return inventory.getQuantity() - reserved;
    }

    // Reserve stock for a specific cart
    @Transactional
    public void reserveStock(String productId, String cartId, int quantity) {
        int available = getAvailableStock(productId);

        if (available < quantity) {
            throw new RuntimeException("Not enough stock to reserve");
        }

        LocalDateTime reservedUntil = LocalDateTime.now().plusMinutes(30);

        // Check if reservation for this cart/product exists
        List<InventoryReservation> reservations = reservationRepository.findByCartId(cartId);
        InventoryReservation existing = reservations.stream()
                .filter(r -> r.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            existing.setReservedUntil(reservedUntil);
            reservationRepository.save(existing);
        } else {
            InventoryReservation reservation = new InventoryReservation();
            reservation.setProductId(productId);
            reservation.setCartId(cartId);
            reservation.setQuantity(quantity);
            reservation.setReservedUntil(reservedUntil);
            reservationRepository.save(reservation);
        }
    }

    // Release all reservations of a cart (e.g., on order completion or cart abandonment)
    @Transactional
    public void releaseReservation(String cartId) {
        reservationRepository.deleteByCartId(cartId);
    }

    // Reduce stock permanently when order is confirmed
    @Transactional
    public void reduceStock(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient quantity in stock");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }
    @Transactional
    public void increaseStock(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);
    }



    // Scheduled task to clear expired reservations every 5 minutes
    @Scheduled(fixedRate = 10000)
    @Transactional
    public void clearExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        List<InventoryReservation> expiredReservations = reservationRepository.findByReservedUntilBefore(now);
        if (!expiredReservations.isEmpty()) {
            reservationRepository.deleteAll(expiredReservations);
        }
    }
}
