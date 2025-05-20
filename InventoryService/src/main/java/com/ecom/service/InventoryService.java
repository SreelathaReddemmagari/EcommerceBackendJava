package com.ecom.service;

import com.ecom.model.Inventory;
import com.ecom.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    //find an inventory using sku code where quantity is >=0
    public boolean isInStock(String skuCode,Integer quantity){
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode,quantity);

    }
    public Inventory addStock(String skuCode, Integer quantityToAdd) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findBySkuCode(skuCode);
        Inventory inventory;
        if (inventoryOpt.isPresent()) {
            inventory = inventoryOpt.get();
            inventory.setQuantity(inventory.getQuantity() + quantityToAdd);
        } else {
            inventory = new Inventory();
            inventory.setSkuCode(skuCode);
            inventory.setQuantity(quantityToAdd);
        }
        return inventoryRepository.save(inventory);
    }

    // Reduce stock quantity for an SKU if enough stock is available
    public boolean reduceStock(String skuCode, Integer quantityToReduce) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findBySkuCode(skuCode);
        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();
            if (inventory.getQuantity() >= quantityToReduce) {
                inventory.setQuantity(inventory.getQuantity() - quantityToReduce);
                inventoryRepository.save(inventory);
                return true;
            }
        }
        return false;
    }

    // Get total quantity available for a given SKU
    public Integer getAvailableQuantity(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode)
                .map(Inventory::getQuantity)
                .orElse(0);
    }

    // Fetch inventory details by SKU code
    public Optional<Inventory> getInventoryBySkuCode(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode);
    }
}
