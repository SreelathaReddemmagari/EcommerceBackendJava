package com.ecom.dto;

import java.util.List;
//This DTO captures the request payload when a user is creating a new cart.
//Contains data like userId and a list of requested items.
public class CartRequestDto {
    private Integer userId;
    private List<CartItemRequestDto> items;

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public List<CartItemRequestDto> getItems() {
        return items;
    }
    public void setItems(List<CartItemRequestDto> items) {
        this.items = items;
    }
}
