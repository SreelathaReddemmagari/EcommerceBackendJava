package com.ecom.repository;

import com.ecom.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    boolean existsByskuCodeAndquantityIsGreaterThanEqual(String skuCode, Integer quantity);
}
