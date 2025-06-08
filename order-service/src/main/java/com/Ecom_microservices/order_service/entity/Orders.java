//package com.Ecom_microservices.order_service.entity;
//
//import com.amazonaws.services.dynamodbv2.datamodeling.*;
//import lombok.Data;
//
//import java.math.BigDecimal;
//
//@DynamoDBTable(tableName = "EcomOrders")
//@Data
//public class Orders {
//    @DynamoDBHashKey(attributeName = "PK")
//    private String pk; // e.g., "o#<orderId>"
//
//    @DynamoDBRangeKey(attributeName = "SK")
//    private String sk; // e.g., "order#<orderId>"
//
//    @DynamoDBAttribute
//    private String id; // Changed from Long to String for consistency
//
//    @DynamoDBAttribute
//    @DynamoDBIndexHashKey(globalSecondaryIndexName = "UserIndex")
//    private String userId;
//
//    private String primaryProductId;
//
//    @DynamoDBAttribute
//    private int paymentId;
//
//    @DynamoDBAttribute
//    @DynamoDBIndexRangeKey(globalSecondaryIndexNames = {"UserIndex", "StatusIndex", "ProductIndex"})
//    private String orderDate; // Store as ISO string (e.g., "2025-05-28T12:00:00")
//
//    @DynamoDBAttribute
//    private BigDecimal totalPrice;
//
//    @DynamoDBAttribute
//    private String createdAt; // Store as ISO string
//
//    @DynamoDBAttribute
//    private String updatedAt; // Store as ISO string
//
//    @DynamoDBAttribute
//    @DynamoDBIndexHashKey(globalSecondaryIndexName = "StatusIndex")
//    private String status; // Moved from OrderStatus to simplify
//}

package com.Ecom_microservices.order_service.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//@DynamoDBTable(tableName = "EcomOrders")
@Data
public class Orders {
    private String id;
    private String userId;
    private String primaryProductId;
    private String paymentId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;


//
//    @DynamoDBHashKey(attributeName = "PK")
//    private String pk; // e.g., "o#<orderId>"
//
//    @DynamoDBRangeKey(attributeName = "SK")
//    private String sk; // e.g., "order#<orderId>"
//
//    @DynamoDBAttribute(attributeName = "id")
//    private String id; // e.g., "123e4567-e89b-12d3-a456-426614174000"
//
//    @DynamoDBAttribute(attributeName = "userId")
//    //@DynamoDBIndexHashKey(globalSecondaryIndexName = "UserIndex")
//    private String userId; // e.g., "U001"
//
//    @DynamoDBAttribute(attributeName = "ProductId")
//    //@DynamoDBIndexHashKey(globalSecondaryIndexName = "ProductIndex")
//    private String primaryProductId; // e.g., "PROD001"
//
//    @DynamoDBAttribute(attributeName = "paymentId")
//    private String paymentId; // e.g., 1234
//
//    @DynamoDBAttribute(attributeName = "orderDate")
//    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
//    //@DynamoDBIndexRangeKey(globalSecondaryIndexNames = {"UserIndex", "StatusIndex", "ProductIndex"})
//    private LocalDateTime orderDate; // e.g., 2025-05-28T17:54:00
//
//    @DynamoDBAttribute(attributeName = "totalPrice")
//    private BigDecimal totalPrice; // e.g., 2500.00
//
//    @DynamoDBAttribute(attributeName = "createdAt")
//    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
//    private LocalDateTime createdAt; // e.g., 2025-05-28T17:54:00
//
//    @DynamoDBAttribute(attributeName = "updatedAt")
//    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
//    private LocalDateTime updatedAt; // e.g., 2025-05-28T17:54:00
//
//    @DynamoDBAttribute(attributeName = "status")
//    // @DynamoDBIndexHashKey(globalSecondaryIndexName = "StatusIndex")
//    private String status; // e.g., "PENDING"
//
//    // Converter for LocalDateTime to String (DynamoDB stores as ISO string)
//    public static class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {
//        @Override
//        public String convert(LocalDateTime object) {
//            return object != null ? object.toString() : null; // ISO-8601 format
//        }
//
//        @Override
//        public LocalDateTime unconvert(String object) {
//            return object != null ? LocalDateTime.parse(object) : null;
//        }
//    }
}

//....................................................................................................................................
//
//    @DynamoDBHashKey(attributeName = "PK")
//    private String pk;
//
//    @DynamoDBRangeKey(attributeName = "SK")
//    private String sk;
//
//    @DynamoDBAttribute
//    private String userId;
//
//    @DynamoDBAttribute
//    private String primaryProductId;
//
//    @DynamoDBAttribute
//    private String paymentId;
//
//    @DynamoDBAttribute
//    private String orderDate;  // or LocalDateTime if you prefer and configure DynamoDB mapper
//
//    @DynamoDBAttribute
//    private BigDecimal totalPrice;
//
//    @DynamoDBAttribute
//    private String status;
//
//    @DynamoDBAttribute
//    private String createdAt;
//
//    @DynamoDBAttribute
//    private String updatedAt;
//
//    // Getters and setters for all fields below
//
//    public String getPk() {
//        return pk;
//    }
//
//    public void setPk(String pk) {
//        this.pk = pk;
//    }
//
//    public String getSk() {
//        return sk;
//    }
//
//    public void setSk(String sk) {
//        this.sk = sk;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getPrimaryProductId() {
//        return primaryProductId;
//    }
//
//    public void setPrimaryProductId(String primaryProductId) {
//        this.primaryProductId = primaryProductId;
//    }
//
//    public String getPaymentId() {
//        return paymentId;
//    }
//
//    public void setPaymentId(String paymentId) {
//        this.paymentId = paymentId;
//    }
//
//    public LocalDateTime getOrderDate() {
//        return orderDate;
//    }
//
//    public void setOrderDate(String orderDate) {
//        this.orderDate = orderDate;
//    }
//
//    public BigDecimal getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(BigDecimal totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//
//}