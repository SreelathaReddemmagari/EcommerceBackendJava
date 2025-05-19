package com.ecom.service;

import com.ecom.dto.Orderrequest;
import com.ecom.model.Order;
import com.ecom.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public void placeOrder(Orderrequest orderrequest){
        //map orderrequest to order object

        //creating order object
        Order order = new Order();
        order.setOrderDate(orderrequest.orderDate());
        order.setOrderNumber(orderrequest.orderNumber());
        order.setPrice(orderrequest.price());
        order.setUserId(orderrequest.userId());
        order.setQuantity(orderrequest.quantity());
        order.setSkuCode(orderrequest.skuCode());
        orderRepository.save(order);
        //save order to order repository


    }
    //list all the orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    //get orders by using id
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    //delete the order
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
