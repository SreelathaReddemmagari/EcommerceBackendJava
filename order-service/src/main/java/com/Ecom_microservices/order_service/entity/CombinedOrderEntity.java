package com.Ecom_microservices.order_service.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@DynamoDBTable(tableName = "EcomOrders")
public class CombinedOrderEntity {

    @DynamoDBHashKey(attributeName = "PK")
    private String pk; // e.g., "o#<orderId>"

    @DynamoDBRangeKey(attributeName = "SK")
    private String sk; // e.g., "order#<orderId>"

    @DynamoDBAttribute
    private String id;

    @DynamoDBAttribute
    private String userId;

    @DynamoDBAttribute
    private String primaryProductId;

    @DynamoDBAttribute
    private String paymentId;

    @DynamoDBAttribute
    private String orderDate; // store as string (ISO)

    @DynamoDBAttribute
    private BigDecimal totalPrice;

    @DynamoDBAttribute
    private String createdAt;

    @DynamoDBAttribute
    private String updatedAt;

    @DynamoDBAttribute
    private String status;

    @DynamoDBAttribute
    private List<OrderItems> orderItems;

    @DynamoDBAttribute
    private List<OrderStatus> statusHistory;
}
