package com.ecom.service;

import com.ecom.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    //find an inventory using sku code where quantity is >=0
    public boolean isInStock(String skuCode,Integer quantity){
        return inventoryRepository.existsByskuCodeAndquantityIsGreaterThanEqual(skuCode,quantity);

    }
}
