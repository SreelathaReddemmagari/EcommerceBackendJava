package com.ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="inventory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

//    @Column(name = "reserved_quantity", nullable = false)
//    private Integer reservedQuantity;

    @Column(name = "reorder_level", nullable = false)
    private Integer reorderLevel;

//    @Column(name = "updated_at", nullable = false)
//    private LocalDateTime updatedAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "status_description")
    private String statusDescription;

//    @PrePersist
//    public void prePersist() {
//        if (updatedAt == null) {
//            updatedAt = LocalDateTime.now();
//        }
//    }
//    @Column(name = "reserved_until")
//    private LocalDateTime reservedUntil;
//
//    @PreUpdate
//    public void preUpdate() {
//        updatedAt = LocalDateTime.now();
//    }

//    @PreUpdate
//    public void preUpdate() {
//        updatedAt = LocalDateTime.now();
//    }
}

