//package com.Ecom_microservices.order_service.dto;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Positive;
//import lombok.Data;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Data
//public class OrderRequestDTO {
//    @NotBlank(message = "User ID cannot be blank")
//    private String userId;
//
//    @NotEmpty(message = "Order items cannot be empty")
//    private List<OrderItemDTO> orderItems;
//
//    @Positive(message = "Total price must be positive")
//    private BigDecimal totalPrice;
//
//    @NotBlank(message = "Status cannot be blank")
//    private String status;
//}
package com.Ecom_microservices.order_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequestDTO {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

   // @NotNull(message = "Total price cannot be null")
//    @Positive(message = "Total price must be positive")
//    private BigDecimal totalPrice;

//    @NotBlank(message = "Status cannot be blank")
//    private String status;

    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<CartItemDTO> orderItems;

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(Long userId, BigDecimal totalPrice, String status, List<CartItemDTO> orderItems) {
        this.userId = userId;
//        this.totalPrice = totalPrice;
//        this.status = status;
        this.orderItems = orderItems;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

//    public BigDecimal getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(BigDecimal totalPrice) {
//        this.totalPrice = totalPrice;
//    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public List<CartItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartItemDTO> orderItems) {
        this.orderItems = orderItems;
    }


}
