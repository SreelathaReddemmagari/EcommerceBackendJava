//package com.Ecom_microservices.order_service.controller;
//
//import com.Ecom_microservices.order_service.dto.OrderRequestDTO;
//import com.Ecom_microservices.order_service.dto.OrderResponseDTO;
//import com.Ecom_microservices.order_service.service.OrderService;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//    private final OrderService orderService;
//
//    public OrderController(OrderService orderService) {
//        this.orderService = orderService;
//    }
//
//    @PostMapping
//    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
//        return new ResponseEntity<>(orderService.createOrder(orderRequestDTO), HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{orderId}")
//    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable String orderId) {
//        return ResponseEntity.ok(orderService.getOrderById(orderId));
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable String userId) {
//        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
//    }
//}

package com.Ecom_microservices.order_service.controller;

import com.Ecom_microservices.order_service.dto.OrderPaymentUpdateDTO;
import com.Ecom_microservices.order_service.dto.OrderRequestDTO;
import com.Ecom_microservices.order_service.dto.OrderResponseDTO;
import com.Ecom_microservices.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        return new ResponseEntity<>(orderService.createOrder(orderRequestDTO), HttpStatus.CREATED);
    }
    @PutMapping("/{orderId}/cancel")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> cancelOrder(
            @PathVariable String orderId,
            @RequestParam(required = false) String reason) {
        try {
            orderService.cancelOrder(orderId, reason);
            return ResponseEntity.ok("Order cancelled successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to cancel order: " + e.getMessage());
        }
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable String orderId) {
        try {
            OrderResponseDTO response = orderService.getOrderById(orderId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable String userId) {
        List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    @PutMapping("/payment/update")
    public ResponseEntity<OrderResponseDTO>PaymentDetails(@RequestBody OrderPaymentUpdateDTO paymentUpdateDTO) {
        try {
            OrderResponseDTO updatedOrder =orderService.updatePaymentDetails(paymentUpdateDTO);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


//    @GetMapping("/{orderId}")
//    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable String orderId) {
//        return ResponseEntity.ok(orderService.getOrderById(orderId));
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable String userId) {
//        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
//    }
}