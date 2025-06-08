package com.Ecom_microservices.order_service.repository;

import com.Ecom_microservices.order_service.entity.CombinedOrderEntity;
import com.Ecom_microservices.order_service.entity.OrderItems;
import com.Ecom_microservices.order_service.entity.OrderStatus;
import com.Ecom_microservices.order_service.entity.Orders;
import com.Ecom_microservices.order_service.utility.OrderMapper;
import com.Ecom_microservices.order_service.utility.OrderWrapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
//    private final DynamoDBMapper dynamoDBMapper;
//
//    public OrderRepository(DynamoDBMapper dynamoDBMapper) {
//        this.dynamoDBMapper = dynamoDBMapper;
//    }
//
//    public void saveOrder(Orders order) {
//        order.setPk("o#" + order.getId());
//        order.setSk("order#" + order.getId());
//        dynamoDBMapper.save(order);
//    }
//
//    public void saveOrderItem(OrderItems item) {
//        item.setPk("o#" + item.getOrderId());
//        item.setSk("item#" + item.getProductId());
//        dynamoDBMapper.save(item);
//    }
//
//    public void saveOrderStatus(OrderStatus status) {
//        status.setPk("o#" + status.getOrderId());
//        status.setSk("status#" + status.getOrderStatusId());
//        dynamoDBMapper.save(status);
//    }
//
//    public Orders findOrderById(String orderId) {
//        return dynamoDBMapper.load(Orders.class, "o#" + orderId, "order#" + orderId);
//    }
//
//    public List<OrderItems> findOrderItemsByOrderId(String orderId) {
//        Map<String, AttributeValue> eav = new HashMap<>();
//        eav.put(":pk", new AttributeValue().withS("o#" + orderId));
//        eav.put(":skPrefix", new AttributeValue().withS("item#"));
//
//        DynamoDBQueryExpression<OrderItems> queryExpression = new DynamoDBQueryExpression<OrderItems>()
//                .withKeyConditionExpression("PK = :pk and begins_with(SK, :skPrefix)")
//                .withExpressionAttributeValues(eav);
//
//        return dynamoDBMapper.query(OrderItems.class, queryExpression);
//    }
//
//    public List<OrderStatus> findOrderStatusesByOrderId(String orderId) {
//        Map<String, AttributeValue> eav = new HashMap<>();
//        eav.put(":pk", new AttributeValue().withS("o#" + orderId));
//        eav.put(":skPrefix", new AttributeValue().withS("status#"));
//
//        DynamoDBQueryExpression<OrderStatus> queryExpression = new DynamoDBQueryExpression<OrderStatus>()
//                .withKeyConditionExpression("PK = :pk and begins_with(SK, :skPrefix)")
//                .withExpressionAttributeValues(eav);
//
//        return dynamoDBMapper.query(OrderStatus.class, queryExpression);
//    }
//
//    public List<Orders> findOrdersByUserId(String userId) {
//        Map<String, AttributeValue> eav = new HashMap<>();
//        eav.put(":userId", new AttributeValue().withS(userId));
//
//        DynamoDBQueryExpression<Orders> queryExpression = new DynamoDBQueryExpression<Orders>()
//                .withIndexName("UserIndex")
//                .withKeyConditionExpression("userId = :userId")
//                .withExpressionAttributeValues(eav)
//                .withConsistentRead(false);
//
//        return dynamoDBMapper.query(Orders.class, queryExpression);
//    }
private final DynamoDBMapper dynamoDBMapper;

    public OrderRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    // Save the full order (Order + Items + StatusHistory in one item)
    public void saveOrderWrapper(OrderWrapper wrapper) {
        CombinedOrderEntity combined = OrderMapper.toCombined(wrapper);
        dynamoDBMapper.save(combined);
    }

    // Load and convert to OrderWrapper
    public OrderWrapper findOrderById(String orderId) {
        CombinedOrderEntity entity = dynamoDBMapper.load(
                CombinedOrderEntity.class,
                "o#" + orderId,
                "order#" + orderId
        );

        if (entity == null) {
            return null;
        }

        return OrderMapper.fromCombined(entity);
    }

    // Optional: Query orders by userId using GSI
    public List<OrderWrapper> findOrdersByUserId(String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":userId", new AttributeValue().withS(userId));

        DynamoDBQueryExpression<CombinedOrderEntity> queryExpression =
                new DynamoDBQueryExpression<CombinedOrderEntity>()
                        .withIndexName("UserIndex")
                        .withKeyConditionExpression("userId = :userId")
                        .withExpressionAttributeValues(eav)
                        .withConsistentRead(false);

        List<CombinedOrderEntity> results = dynamoDBMapper.query(CombinedOrderEntity.class, queryExpression);

        return results.stream()
                .map(OrderMapper::fromCombined)
                .collect(Collectors.toList());
    }

    public OrderWrapper findOrderWrapperById(String orderId) {
        CombinedOrderEntity entity = dynamoDBMapper.load(
                CombinedOrderEntity.class,
                "o#" + orderId,
                "order#" + orderId
        );

        if (entity == null) {
            System.out.println("No order found with ID: " + orderId);
            return null;
        }

        return OrderMapper.fromCombined(entity);
    }
}