//package com.ecom.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "cart_items")
//public class CartItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "cart_item_id")
//    private Integer cartItemId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cart_id", nullable = false)
//    @JsonBackReference
//    private Cart cart;
//
//    @Column(name = "product_id", nullable = false)
//    private Long productId;
//
//    @Column(name = "product_name")
//    private String productName;
//
//    @Column(name = "unit_price")
//    private Double unitPrice;
//
//    @Column(name = "quantity", nullable = false)
//    private Integer quantity;
//
//    // Getters and Setters
//
//    public Integer getCartItemId() {
//        return cartItemId;
//    }
//
//    public void setCartItemId(Integer cartItemId) {
//        this.cartItemId = cartItemId;
//    }
//
//    public Cart getCart() {
//        return cart;
//    }
//
//    public void setCart(Cart cart) {
//        this.cart = cart;
//    }
//
//    public Long getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public Double getUnitPrice() {
//        return unitPrice;
//    }
//
//    public void setUnitPrice(Double unitPrice) {
//        this.unitPrice = unitPrice;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//}
package com.ecom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
//cartitem model
@Document(collection = "cart_items")
public class CartItem {

    @Id
    private String cartItemId;

    @DBRef(lazy = true)
    @JsonIgnore
    private Cart cart;

    private Long productId;

    private String productName;

    private Double unitPrice;

    private Integer quantity;

    // Getters and Setters

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
