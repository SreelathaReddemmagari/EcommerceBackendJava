package com.ecom.dto;

public class ProductDto {
    private Long id;
    private String name;
    private Double price;
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for price
    public Double getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(Double price) {
        this.price = price;
    }
}
