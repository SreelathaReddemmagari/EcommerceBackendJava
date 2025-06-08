package com.ecom.repository;

import com.ecom.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
//    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);
//
//    Optional<Inventory> findBySkuCode(String skuCode);
     Optional<Inventory> findBySku(String sku);

     Optional<Inventory> findByProductId(String productId);
}
