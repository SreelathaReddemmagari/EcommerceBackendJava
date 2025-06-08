package com.Ecom_microservices.order_service.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import java.math.BigDecimal;

//@DynamoDBTable(tableName = "EcomOrders")
@Data
@DynamoDBDocument
public class OrderItems {
    private int orderItemId;
    private String productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
//    @DynamoDBHashKey(attributeName = "PK")
//    private String pk; // e.g., "o#<orderId>"
//
//    @DynamoDBRangeKey(attributeName = "SK")
//    private String sk; // e.g., "item#<productId>"
//
//    @DynamoDBAttribute
//    private int orderItemId; // Renamed to camelCase
//
//    @DynamoDBAttribute
    private String orderId; // Changed to String to match Orders.id
//
//    @DynamoDBAttribute
//    @DynamoDBIndexHashKey(globalSecondaryIndexName = "ProductIndex")
//    private String productId; // Changed to String to match Product Service
//
//    @DynamoDBAttribute
//    private int quantity;
//
//    @DynamoDBAttribute
//    private BigDecimal price;
//
//    @DynamoDBAttribute
//    private String productName; // Snapshot from Product Service
public int getOrderItemId() {
    return orderItemId;
}

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}