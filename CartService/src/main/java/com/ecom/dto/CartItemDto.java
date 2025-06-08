package com.ecom.dto;

import com.ecom.model.CartItem;

public class CartItemDto {

    public static class CartItemDTO {
        private String cartItemId;
        private Long productId;
        private String productName;
        private Double unitPrice;
        private Integer quantity;

        public CartItemDTO(CartItem item) {
            this.cartItemId = item.getCartItemId();
            this.productId = item.getProductId();
            this.productName = item.getProductName();
            this.unitPrice = item.getUnitPrice();
            this.quantity = item.getQuantity();
        }

        // Getters and Setters (required for serialization)
        public String getCartItemId() { return cartItemId; }
        public void setCartItemId(String cartItemId) { this.cartItemId = cartItemId; }

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        public Double getUnitPrice() { return unitPrice; }
        public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
        // Getters and setters
    }


