///*
//package com.Ecom_microservices.order_service.service;
//
//import com.Ecom_microservices.order_service.dto.OrderItemResponseDTO;
//import com.Ecom_microservices.order_service.dto.OrderRequestDTO;
//import com.Ecom_microservices.order_service.dto.OrderResponseDTO;
//import com.Ecom_microservices.order_service.dto.OrderStatusDTO;
//import com.Ecom_microservices.order_service.dto.ProductDTO;
//import com.Ecom_microservices.order_service.entity.OrderItems;
//import com.Ecom_microservices.order_service.entity.OrderStatus;
//import com.Ecom_microservices.order_service.entity.Orders;
//import com.Ecom_microservices.order_service.repository.OrderRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderServiceImpl implements OrderService {
//    private final OrderRepository orderRepository;
//    private final RestTemplate restTemplate;
//    private static final String PRODUCT_SERVICE_URL = "http://localhost:8080/api/product";
//
//    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
//        this.orderRepository = orderRepository;
//        this.restTemplate = restTemplate;
//    }
//
//    @Override
//    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
//        // Generate order ID
//        String orderId = UUID.randomUUID().toString();
//        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//
//        // Create Orders entity
//        Orders order = new Orders();
//        order.setId(orderId);
//        order.setUserId(orderRequestDTO.getUserId());
//        order.setPaymentId(generatePaymentId());
//        order.setOrderDate(now);
//        order.setTotalPrice(orderRequestDTO.getTotalPrice());
//        order.setStatus(orderRequestDTO.getStatus());
//        order.setCreatedAt(now);
//        order.setUpdatedAt(now);
//        orderRepository.saveOrder(order);
//
//        // Create OrderItems
//        List<OrderItemResponseDTO> itemResponses = orderRequestDTO.getOrderItems().stream()
//                .map(itemDTO -> {
//                    ProductDTO product;
//                    try {
//                        product = restTemplate.getForObject(
//                                PRODUCT_SERVICE_URL + itemDTO.getProductId(), ProductDTO.class);
//                    } catch (RestClientException e) {
//                        throw new RuntimeException("Failed to fetch product: " + itemDTO.getProductId(), e);
//                    }
//                    if (product == null) {
//                        throw new RuntimeException("Product not found: " + itemDTO.getProductId());
//                    }
//
//                    OrderItems item = new OrderItems();
//                    item.setOrderItemId(generateOrderItemId());
//                    item.setOrderId(orderId);
//                    item.setProductId(itemDTO.getProductId());
//                    item.setQuantity(itemDTO.getQuantity());
//                    item.setPrice(product.getPrice());
//                    item.setProductName(product.getName());
//                    orderRepository.saveOrderItem(item);
//
//                    OrderItemResponseDTO itemResponse = new OrderItemResponseDTO();
//                    itemResponse.setOrderItemId(item.getOrderItemId());
//                    itemResponse.setProductId(item.getProductId());
//                    itemResponse.setProductName(item.getProductName());
//                    itemResponse.setQuantity(item.getQuantity());
//                    itemResponse.setPrice(item.getPrice());
//                    return itemResponse;
//                })
//                .collect(Collectors.toList());
//
//        // Create initial OrderStatus
//        OrderStatus status = new OrderStatus();
//        status.setOrderStatusId(generateOrderStatusId());
//        status.setOrderId(orderId);
//        status.setStatusName(orderRequestDTO.getStatus());
//        status.setStatusDescription("Order " + orderRequestDTO.getStatus().toLowerCase());
//        status.setStatusDate(now);
//        status.setCreatedAt(now);
//        status.setUpdatedAt(now);
//        orderRepository.saveOrderStatus(status);
//
//        // Build response
//        OrderResponseDTO response = new OrderResponseDTO();
//        response.setOrderId(orderId);
//        response.setUserId(order.getUserId());
//        response.setPaymentId(order.getPaymentId());
//        response.setOrderDate(LocalDateTime.parse(order.getOrderDate()));
//        response.setTotalPrice(order.getTotalPrice());
//        response.setStatus(order.getStatus());
//        response.setCreatedAt(LocalDateTime.parse(order.getCreatedAt()));
//        response.setUpdatedAt(LocalDateTime.parse(order.getUpdatedAt()));
//        response.setOrderItems(itemResponses);
//        response.setOrderStatuses(List.of(mapToOrderStatusDTO(status)));
//        return response;
//    }
//
//    @Override
//    public OrderResponseDTO getOrderById(String orderId) {
//        Orders order = orderRepository.findOrderById(orderId);
//        if (order == null) {
//            throw new RuntimeException("Order not found: " + orderId);
//        }
//
//        List<OrderItems> items = orderRepository.findOrderItemsByOrderId(orderId);
//        List<OrderStatus> statuses = orderRepository.findOrderStatusesByOrderId(orderId);
//
//        OrderResponseDTO response = new OrderResponseDTO();
//        response.setOrderId(order.getId());
//        response.setUserId(order.getUserId());
//        response.setPaymentId(order.getPaymentId());
//        response.setOrderDate(LocalDateTime.parse(order.getOrderDate()));
//        response.setTotalPrice(order.getTotalPrice());
//        response.setStatus(order.getStatus());
//        response.setCreatedAt(LocalDateTime.parse(order.getCreatedAt()));
//        response.setUpdatedAt(LocalDateTime.parse(order.getUpdatedAt()));
//        response.setOrderItems(items.stream().map(this::mapToOrderItemResponseDTO).collect(Collectors.toList()));
//        response.setOrderStatuses(statuses.stream().map(this::mapToOrderStatusDTO).collect(Collectors.toList()));
//        return response;
//    }
//
//    @Override
//    public List<OrderResponseDTO> getOrdersByUserId(String userId) {
//        List<Orders> orders = orderRepository.findOrdersByUserId(userId);
//        return orders.stream().map(this::mapToOrderResponseDTO).collect(Collectors.toList());
//    }
//
//    private OrderResponseDTO mapToOrderResponseDTO(Orders order) {
//        OrderResponseDTO response = new OrderResponseDTO();
//        response.setOrderId(order.getId());
//        response.setUserId(order.getUserId());
//        response.setPaymentId(order.getPaymentId());
//        response.setOrderDate(LocalDateTime.parse(order.getOrderDate()));
//        response.setTotalPrice(order.getTotalPrice());
//        response.setStatus(order.getStatus());
//        response.setCreatedAt(LocalDateTime.parse(order.getCreatedAt()));
//        response.setUpdatedAt(LocalDateTime.parse(order.getUpdatedAt()));
//        response.setOrderItems(orderRepository.findOrderItemsByOrderId(order.getId())
//                .stream().map(this::mapToOrderItemResponseDTO).collect(Collectors.toList()));
//        response.setOrderStatuses(orderRepository.findOrderStatusesByOrderId(order.getId())
//                .stream().map(this::mapToOrderStatusDTO).collect(Collectors.toList()));
//        return response;
//    }
//
//    private OrderItemResponseDTO mapToOrderItemResponseDTO(OrderItems item) {
//        OrderItemResponseDTO dto = new OrderItemResponseDTO();
//        dto.setOrderItemId(item.getOrderItemId());
//        dto.setProductId(item.getProductId());
//        dto.setProductName(item.getProductName());
//        dto.setQuantity(item.getQuantity());
//        dto.setPrice(item.getPrice());
//        return dto;
//    }
//
//    private OrderStatusDTO mapToOrderStatusDTO(OrderStatus status) {
//        OrderStatusDTO dto = new OrderStatusDTO();
//        dto.setOrderStatusId(status.getOrderStatusId());
//        dto.setStatusName(status.getStatusName());
//        dto.setStatusDescription(status.getStatusDescription());
//        dto.setStatusDate(LocalDateTime.parse(status.getStatusDate()));
//        dto.setCreatedAt(LocalDateTime.parse(status.getCreatedAt()));
//        dto.setUpdatedAt(LocalDateTime.parse(status.getUpdatedAt()));
//        return dto;
//    }
//
//    private int generatePaymentId() {
//        return (int) (Math.random() * 10000);
//    }
//
//    private int generateOrderItemId() {
//        return (int) (Math.random() * 10000);
//    }
//
//    private int generateOrderStatusId() {
//        return (int) (Math.random() * 10000);
//    }
//}*/
//
//package com.Ecom_microservices.order_service.service;
//
//import com.Ecom_microservices.order_service.dto.OrderItemResponseDTO;
//import com.Ecom_microservices.order_service.dto.OrderRequestDTO;
//import com.Ecom_microservices.order_service.dto.OrderResponseDTO;
//import com.Ecom_microservices.order_service.dto.OrderStatusDTO;
//import com.Ecom_microservices.order_service.dto.ProductDTO;
//import com.Ecom_microservices.order_service.entity.OrderItems;
//import com.Ecom_microservices.order_service.entity.OrderStatus;
//import com.Ecom_microservices.order_service.entity.Orders;
//import com.Ecom_microservices.order_service.repository.OrderRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderServiceImpl implements OrderService {
//    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
//
//    private final OrderRepository orderRepository;
//    private final RestTemplate restTemplate;
//
//    @Value("${product.service.url:http://product-service:8080/api/product}")
//    private String productServiceUrl;
//
//    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
//        this.orderRepository = orderRepository;
//        this.restTemplate = restTemplate;
//    }
//
//    @Override
//    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
//        logger.info("Creating order for user: {}", orderRequestDTO.getUserId());
//
//        // Generate order ID
//        String orderId = UUID.randomUUID().toString();
//        LocalDateTime now = LocalDateTime.now(); // 2025-05-28T17:56:00 IST
//
//        // Validate order items and fetch product details
//        List<OrderItemResponseDTO> itemResponses = orderRequestDTO.getOrderItems().stream()
//                .map(itemDTO -> {
//                    ProductDTO product;
//                    try {
//                        String url = productServiceUrl + "/" + itemDTO.getProductId();
//                        logger.debug("Fetching product details from: {}", url);
//                        product = restTemplate.getForObject(url, ProductDTO.class);
//                    } catch (RestClientException e) {
//                        logger.error("Failed to fetch product {}: {}", itemDTO.getProductId(), e.getMessage());
//                        throw new RuntimeException("Failed to fetch product " + itemDTO.getProductId() + ": " + e.getMessage(), e);
//                    }
//                    if (product == null) {
//                        logger.error("Product not found: {}", itemDTO.getProductId());
//                        throw new RuntimeException("Product not found: " + itemDTO.getProductId());
//                    }
//
//                    OrderItems item = new OrderItems();
//                    item.setOrderItemId(Integer.parseInt(UUID.randomUUID().toString()));
//                    item.setOrderId(orderId);
//                    item.setProductId(itemDTO.getProductId());
//                    item.setQuantity(itemDTO.getQuantity());
//                    item.setPrice(new BigDecimal(String.valueOf(product.getPrice())));
//                    item.setProductName(product.getName());
//                    orderRepository.saveOrderItem(item);
//
//                    OrderItemResponseDTO itemResponse = new OrderItemResponseDTO();
//                    itemResponse.setOrderItemId(item.getOrderItemId());
//                    itemResponse.setProductId(item.getProductId());
//                    itemResponse.setProductName(item.getProductName());
//                    itemResponse.setQuantity(item.getQuantity());
//                    itemResponse.setPrice(item.getPrice());
//                    return itemResponse;
//                })
//                .collect(Collectors.toList());
//
//        // Validate total price
//        BigDecimal calculatedTotal = itemResponses.stream()
//                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        if (calculatedTotal.compareTo(orderRequestDTO.getTotalPrice()) != 0) {
//            logger.warn("Total price mismatch: calculated={}, provided={}", calculatedTotal, orderRequestDTO.getTotalPrice());
//            throw new IllegalArgumentException("Total price mismatch: calculated " + calculatedTotal + ", provided " + orderRequestDTO.getTotalPrice());
//        }
//
//        // Create Orders entity
//        Orders order = new Orders();
//        order.setId(orderId);
//        order.setPk("o#" + orderId);
//        order.setSk("order#" + orderId);
//        order.setUserId(orderRequestDTO.getUserId());
//        order.setPrimaryProductId(itemResponses.isEmpty() ? null : itemResponses.get(0).getProductId());
//        order.setPaymentId(generatePaymentId());
//        order.setOrderDate(now);
//        order.setTotalPrice(orderRequestDTO.getTotalPrice());
//        order.setStatus(orderRequestDTO.getStatus());
//        order.setCreatedAt(now);
//        order.setUpdatedAt(now);
//        orderRepository.saveOrder(order);
//
//        // Create initial OrderStatus
//        OrderStatus status = new OrderStatus();
//        status.setOrderStatusId(Integer.parseInt(UUID.randomUUID().toString()));
//        status.setOrderId(orderId);
//        status.setStatusName(orderRequestDTO.getStatus());
//        status.setStatusDescription("Order " + orderRequestDTO.getStatus().toLowerCase());
//        status.setStatusDate(String.valueOf(now));
//        status.setCreatedAt(String.valueOf(now));
//        status.setUpdatedAt(String.valueOf(now));
//        orderRepository.saveOrderStatus(status);
//
//        // Build response
//        OrderResponseDTO response = new OrderResponseDTO();
//        response.setOrderId(orderId);
//        response.setUserId(order.getUserId());
//        response.setPaymentId(order.getPaymentId());
//        response.setOrderDate(order.getOrderDate());
//        response.setTotalPrice(order.getTotalPrice());
//        response.setStatus(order.getStatus());
//        response.setCreatedAt(order.getCreatedAt());
//        response.setUpdatedAt(order.getUpdatedAt());
//        response.setOrderItems(itemResponses);
//        response.setOrderStatuses(List.of(mapToOrderStatusDTO(status)));
//        logger.info("Order created successfully: {}", orderId);
//        return response;
//    }
//
//    @Override
//    public OrderResponseDTO getOrderById(String orderId) {
//        logger.info("Fetching order by ID: {}", orderId);
//        Orders order = orderRepository.findOrderById(orderId);
//        if (order == null) {
//            logger.error("Order not found: {}", orderId);
//            throw new RuntimeException("Order not found: " + orderId);
//        }
//
//        List<OrderItems> items = orderRepository.findOrderItemsByOrderId(orderId);
//        List<OrderStatus> statuses = orderRepository.findOrderStatusesByOrderId(orderId);
//
//        OrderResponseDTO response = new OrderResponseDTO();
//        response.setOrderId(order.getId());
//        response.setUserId(order.getUserId());
//        response.setPaymentId(order.getPaymentId());
//        response.setOrderDate(order.getOrderDate());
//        response.setTotalPrice(order.getTotalPrice());
//        response.setStatus(order.getStatus());
//        response.setCreatedAt(order.getCreatedAt());
//        response.setUpdatedAt(order.getUpdatedAt());
//        response.setOrderItems(items.stream().map(this::mapToOrderItemResponseDTO).collect(Collectors.toList()));
//        response.setOrderStatuses(statuses.stream().map(this::mapToOrderStatusDTO).collect(Collectors.toList()));
//        logger.info("Order fetched successfully: {}", orderId);
//        return response;
//    }
//
//    @Override
//    public List<OrderResponseDTO> getOrdersByUserId(String userId) {
//        logger.info("Fetching orders for user: {}", userId);
//        List<Orders> orders = orderRepository.findOrdersByUserId(userId);
//
//        // Batch fetch items and statuses for all orders
//        List<String> orderIds = orders.stream().map(Orders::getId).collect(Collectors.toList());
//        List<OrderItems> allItems = orderIds.stream()
//                .flatMap(id -> orderRepository.findOrderItemsByOrderId(id).stream())
//                .collect(Collectors.toList());
//        List<OrderStatus> allStatuses = orderIds.stream()
//                .flatMap(id -> orderRepository.findOrderStatusesByOrderId(id).stream())
//                .collect(Collectors.toList());
//
//        return orders.stream().map(order -> {
//            List<OrderItemResponseDTO> items = allItems.stream()
//                    .filter(item -> item.getOrderId().equals(order.getId()))
//                    .map(this::mapToOrderItemResponseDTO)
//                    .collect(Collectors.toList());
//            List<OrderStatusDTO> statuses = allStatuses.stream()
//                    .filter(status -> status.getOrderId().equals(order.getId()))
//                    .map(this::mapToOrderStatusDTO)
//                    .collect(Collectors.toList());
//
//            OrderResponseDTO response = new OrderResponseDTO();
//            response.setOrderId(order.getId());
//            response.setUserId(order.getUserId());
//            response.setPaymentId(order.getPaymentId());
//            response.setOrderDate(order.getOrderDate());
//            response.setTotalPrice(order.getTotalPrice());
//            response.setStatus(order.getStatus());
//            response.setCreatedAt(order.getCreatedAt());
//            response.setUpdatedAt(order.getUpdatedAt());
//            response.setOrderItems(items);
//            response.setOrderStatuses(statuses);
//            return response;
//        }).collect(Collectors.toList());
//    }
//
//    private OrderItemResponseDTO mapToOrderItemResponseDTO(OrderItems item) {
//        OrderItemResponseDTO dto = new OrderItemResponseDTO();
//        dto.setOrderItemId(item.getOrderItemId());
//        dto.setProductId(item.getProductId());
//        dto.setProductName(item.getProductName());
//        dto.setQuantity(item.getQuantity());
//        dto.setPrice(item.getPrice());
//        return dto;
//    }
//
//    private OrderStatusDTO mapToOrderStatusDTO(OrderStatus status) {
//        OrderStatusDTO dto = new OrderStatusDTO();
//        dto.setOrderStatusId(status.getOrderStatusId());
//        dto.setStatusName(status.getStatusName());
//        dto.setStatusDescription(status.getStatusDescription());
//        dto.setStatusDate(LocalDateTime.parse(status.getStatusDate()));
//        dto.setCreatedAt(LocalDateTime.parse(status.getCreatedAt()));
//        dto.setUpdatedAt(LocalDateTime.parse(status.getUpdatedAt()));
//        return dto;
//    }
//
//    private int generatePaymentId() {
//        // Replace with a more robust mechanism in production (e.g., external payment service)
//        return Math.abs(UUID.randomUUID().hashCode());
//    }
//}
package com.Ecom_microservices.order_service.service;

import com.Ecom_microservices.order_service.client.CartServiceClient;
import com.Ecom_microservices.order_service.client.InventoryClient;
import com.Ecom_microservices.order_service.dto.*;
import com.Ecom_microservices.order_service.entity.OrderItems;
import com.Ecom_microservices.order_service.entity.OrderStatus;
import com.Ecom_microservices.order_service.entity.Orders;
import com.Ecom_microservices.order_service.repository.OrderRepository;
import com.Ecom_microservices.order_service.utility.OrderWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final CartServiceClient cartItemClient;
    private final InventoryClient inventoryClient;

    public OrderServiceImpl(OrderRepository orderRepository, CartServiceClient cartItemClient,InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.cartItemClient = cartItemClient;
        this.inventoryClient= inventoryClient;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO.getOrderItems() == null || orderRequestDTO.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be null or empty");
        }

        String orderId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        List<CartItemDTO> userCart = cartItemClient.getCartItems(orderRequestDTO.getUserId());

        List<OrderItems> orderItems = new ArrayList<>();
        List<OrderItemResponseDTO> itemResponses = new ArrayList<>();
        System.out.println("Received order items:");
        for (CartItemDTO itemDTO : orderRequestDTO.getOrderItems()) {
            CartItemDTO product = userCart.stream()
                    .filter(p -> p.getProductId().equals(itemDTO.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found in cart: " + itemDTO.getProductId()));

            OrderItems item = new OrderItems();
            item.setOrderItemId(Math.abs(UUID.randomUUID().hashCode()));
            item.setOrderId(orderId);
            item.setProductId(itemDTO.getProductId());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(BigDecimal.valueOf(product.getUnitPrice()));
            item.setProductName(product.getProductName());

            orderItems.add(item);

            OrderItemResponseDTO itemResponse = new OrderItemResponseDTO();
            itemResponse.setOrderItemId(item.getOrderItemId());
            itemResponse.setProductId(Long.valueOf(item.getProductId()));
            itemResponse.setProductName(item.getProductName());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPrice(item.getPrice());
            itemResponses.add(itemResponse);
            System.out.println(item.getQuantity());
        }


        BigDecimal total = itemResponses.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Orders order = new Orders();
        order.setId(orderId);
        order.setUserId(orderRequestDTO.getUserId().toString());
        order.setPrimaryProductId(itemResponses.get(0).getProductId().toString());
        order.setPaymentId(generatePaymentId());
        order.setOrderDate(now);
        order.setTotalPrice(total);
        order.setStatus("PENDING");
        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        OrderStatus status = new OrderStatus();
        status.setOrderStatusId(Math.abs(UUID.randomUUID().hashCode()));
        status.setOrderId(orderId);
        status.setStatusName("PENDING");
        status.setStatusDescription("Order pending");
        status.setStatusDate(now.toString());
        status.setCreatedAt(now.toString());
        status.setUpdatedAt(now.toString());

        OrderWrapper wrapper = new OrderWrapper();
        wrapper.setOrder(order);
        wrapper.setItems(orderItems);
        wrapper.setStatuses(List.of(status));

        orderRepository.saveOrderWrapper(wrapper);

        // Reduce stock permanently and release reservation
        for (CartItemDTO itemDTO : orderRequestDTO.getOrderItems()) {
            inventoryClient.reduceStock(itemDTO.getProductId(), itemDTO.getQuantity());
        }
        String cartId = orderRequestDTO.getOrderItems().get(0).getCartId();
        inventoryClient.releaseReservation(cartId);

        cartItemClient.clearCart(orderRequestDTO.getUserId());

        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(orderId);
        response.setUserId(orderRequestDTO.getUserId());
        response.setOrderDate(order.getOrderDate());
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());
        response.setOrderItems(itemResponses);
        response.setPaymentId(order.getPaymentId());
        response.setOrderStatuses(List.of(mapToOrderStatusDTO(status)));

        return response;
    }

    @Override
    public void cancelOrder(String orderId, String reason) {
        OrderWrapper wrapper = orderRepository.findOrderById(orderId);
        if (wrapper == null || wrapper.getOrder() == null) {
            throw new RuntimeException("Order not found");
        }

        Orders order = wrapper.getOrder();

        if ("CANCELLED".equalsIgnoreCase(order.getStatus()) || "DELIVERED".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Order cannot be cancelled");
        }

        order.setStatus("CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());

        OrderStatus status = new OrderStatus();
        status.setOrderStatusId(Math.abs(UUID.randomUUID().hashCode()));
        status.setOrderId(orderId);
        status.setStatusName("CANCELLED");
        status.setStatusDescription(reason != null ? reason : "Order cancelled");
        status.setStatusDate(LocalDateTime.now().toString());
        status.setCreatedAt(LocalDateTime.now().toString());
        status.setUpdatedAt(LocalDateTime.now().toString());

        wrapper.getStatuses().add(status);
        orderRepository.saveOrderWrapper(wrapper);

        // Restore inventory
        for (OrderItems item : wrapper.getItems()) {
            inventoryClient.increaseStock(item.getProductId(), item.getQuantity());
        }
    }

    private OrderStatusDTO mapToOrderStatusDTO(OrderStatus status) {
        OrderStatusDTO dto = new OrderStatusDTO();
        dto.setOrderStatusId(status.getOrderStatusId());
        dto.setStatusName(status.getStatusName());
        dto.setStatusDescription(status.getStatusDescription());
        dto.setStatusDate(LocalDateTime.parse(status.getStatusDate()));
        dto.setCreatedAt(LocalDateTime.parse(status.getCreatedAt()));
        dto.setUpdatedAt(LocalDateTime.parse(status.getUpdatedAt()));
        return dto;
    }

    private String generatePaymentId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}