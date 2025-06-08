package com.ecom.repository;

import com.ecom.model.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryReservationRepository extends JpaRepository<InventoryReservation,Long> {
    List<InventoryReservation> findByProductIdAndReservedUntilAfter(String productId, LocalDateTime now);

    List<InventoryReservation> findByReservedUntilBefore(LocalDateTime now);

    List<InventoryReservation> findByCartId(String cartId);

    void deleteByCartId(String cartId);


}
