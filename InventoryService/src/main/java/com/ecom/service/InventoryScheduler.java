//package com.ecom.service;
//
//import com.ecom.model.Inventory;
//import com.ecom.repository.InventoryRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class InventoryScheduler {
//    @Autowired
//    private InventoryRepository inventoryRepository;
//
//    @Scheduled(fixedRate = 5 * 60 * 1000) // every 5 minutes
//    public void clearExpiredReservations() {
//        List<Inventory> all = inventoryRepository.findAll();
//        LocalDateTime now = LocalDateTime.now();
//
//        for (Inventory inv : all) {
//            if (inv.getReservedUntil() != null && inv.getReservedUntil().isBefore(now)) {
//                inv.setQuantity(inv.getQuantity() + inv.getReservedQuantity());
//                inv.setReservedQuantity(0);
//                inv.setReservedUntil(null);
//                inventoryRepository.save(inv);
//            }
//        }
//    }
//}
