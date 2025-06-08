package com.Ecom_microservices.order_service.dto;

public class CartItemDTO {
    private String productId;
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    private String cartId;

    // Constructors
    public CartItemDTO() {
    }

    public CartItemDTO(Long productId, String productName, Double unitPrice, Integer quantity,String cartId) {
        this.productId= String.valueOf(productId);
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.cartId=cartId;

    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = String.valueOf(productId);
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

    public String getCartId() {
        return cartId;
    }
}
