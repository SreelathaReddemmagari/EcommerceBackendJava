package com.ecom.dto;

import java.math.BigDecimal;
import java.util.Date;

public record Orderrequest(Long id,Integer userId,String orderNumber,String skuCode,Date orderDate, double totalAmount,BigDecimal price,Integer quantity) {
}
