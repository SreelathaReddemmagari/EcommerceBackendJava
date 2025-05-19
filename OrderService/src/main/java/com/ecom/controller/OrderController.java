package com.ecom.controller;

import com.ecom.dto.Orderrequest;
import com.ecom.model.Order;
import com.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/place")
    ResponseEntity<String>placeOrder(@RequestBody Orderrequest orderrequest){
        orderService.placeOrder(orderrequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("order placed successfully");
    }
//    public String placeOrder(@RequestBody Orderrequest orderrequest){
//
//
//    }
//}
    //get orders by order id
@GetMapping("/{id}")
public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    Optional<Order> order = orderService.getOrderById(id);
    return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
}

//get all orders
    @GetMapping("/completed")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if (deleted) {
            return ResponseEntity.ok("Order deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order not found with ID: " + id);
        }
    }

}
