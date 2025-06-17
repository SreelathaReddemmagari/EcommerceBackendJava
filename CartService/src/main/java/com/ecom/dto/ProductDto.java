package com.ecom.dto;
//This DTO represents a product retrieved from another microservice (likely via Feign Client).
//Encapsulates product fields like id, name, price, etc.
//Ensures service-to-service communication happens in a standardized, versioned, and controlled format.
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
