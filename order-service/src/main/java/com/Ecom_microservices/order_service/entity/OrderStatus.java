package com.Ecom_microservices.order_service.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

//@DynamoDBTable(tableName = "EcomOrders")
@Data
@DynamoDBDocument
public class OrderStatus {
    private int orderStatusId;
    private String statusName;
    private String statusDescription;
    private String statusDate;
    private String createdAt;
    private String updatedAt;
//    @DynamoDBHashKey(attributeName = "PK")
//    private String pk; // e.g., "o#<orderId>"
//
//    @DynamoDBRangeKey(attributeName = "SK")
//    private String sk; // e.g., "status#<orderStatusId>"
//
//    @DynamoDBAttribute
//    private int orderStatusId; // Renamed to camelCase
//
//    @DynamoDBAttribute
    private String orderId; // Changed to String to match Orders.id
//
//    @DynamoDBAttribute
//    @DynamoDBIndexHashKey(globalSecondaryIndexName = "StatusIndex")
//    private String statusName;
//
//    @DynamoDBAttribute
//    private String statusDescription; // Renamed to camelCase
//
//    @DynamoDBAttribute
//    private String statusDate; // Store as ISO string
//
//    @DynamoDBAttribute
//    private String createdAt; // Store as ISO string, renamed to camelCase
//
//    @DynamoDBAttribute
//    private String updatedAt; // Store as ISO string, renamed to camelCase
}