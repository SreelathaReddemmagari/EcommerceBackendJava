package com.Ecom_microservices.order_service.utility;

import com.Ecom_microservices.order_service.entity.OrderItems;
import com.Ecom_microservices.order_service.entity.OrderStatus;
import com.Ecom_microservices.order_service.entity.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrderWrapper {

        private Orders order;
        private List<OrderItems> orderItems;
        private List<OrderStatus> statusHistory;
        public Orders getOrder() {
                return order;
        }

        public void setOrder(Orders order) {
                this.order = order;
        }

        // Getter and Setter for orderItems
        public List<OrderItems> getOrderItems() {
                return orderItems;
        }

        public void setOrderItems(List<OrderItems> orderItems) {
                this.orderItems = orderItems;
        }

        // Custom setter alias for orderItems
        public void setItems(List<OrderItems> orderItems) {
                this.orderItems = orderItems;
        }
        public List<OrderItems> getItems() {
                return this.orderItems;
        }

        // Getter and Setter for statusHistory
        public List<OrderStatus> getStatusHistory() {
                return statusHistory;
        }

        public void setStatusHistory(List<OrderStatus> statusHistory) {
                this.statusHistory = statusHistory;
        }

        // Custom setter alias for statusHistory
        public void setStatuses(List<OrderStatus> statusHistory) {
                this.statusHistory = statusHistory;
        }

        public List<OrderStatus> getStatuses() {
                return this.statusHistory;
        }


}
