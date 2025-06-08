//package com.ecom.model;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//@Table(name = "cart")
//public class Cart {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "cart_id")
//    private Integer cartId;
//
//    @Column(name = "user_id", nullable = false)
//    private Integer userId;
//
//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at", nullable = false)
//    private LocalDateTime updatedAt;
//
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<CartItem> cartItems;
//
//    // Getters and Setters
//
//    public Integer getCartId() {
//        return cartId;
//    }
//
//    public void setCartId(Integer cartId) {
//        this.cartId = cartId;
//    }
//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public List<CartItem> getCartItems() {
//        return cartItems;
//    }
//
//    public void setCartItems(List<CartItem> cartItems) {
//        this.cartItems = cartItems;
//    }
//}
package com.ecom.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "cart")
public class Cart {
    @Id
    private String cartId;

    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartItem> cartItems; // Embedded directly

    // Getters and Setters

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
       this.cartId=cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
