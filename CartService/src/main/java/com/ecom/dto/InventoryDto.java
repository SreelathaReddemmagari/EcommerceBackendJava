package com.ecom.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InventoryDto {
//    private String productId;
//    private int quantity;
//    private int reservedQuantity;
//    private LocalDateTime reservedUntil;
private String productId;
    private int quantity; // total stock quantity

    // Total reserved quantity (sum of all active reservations)
    private int totalReservedQuantity;

    // List of active reservations for this product (optional)
    private List<ReservationDto> activeReservations;
}


