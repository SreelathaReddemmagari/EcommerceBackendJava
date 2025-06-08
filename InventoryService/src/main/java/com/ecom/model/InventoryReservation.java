package com.ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "cart_id", nullable = false)
    private String cartId;  // identifier for user's cart or session

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "reserved_until", nullable = false)
    private LocalDateTime reservedUntil;
}

