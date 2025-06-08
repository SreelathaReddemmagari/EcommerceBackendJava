package com.ecom.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDto {
    private String cartId;          // Which cart reserved this stock
    private int quantity;           // Quantity reserved
    private LocalDateTime reservedUntil;
}
