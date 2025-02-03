package com.eeibra.ecommerce.notification.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Order {
    private String id;
    private String customerId;
    private String productId;
    private Integer quantity;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private String status;
}