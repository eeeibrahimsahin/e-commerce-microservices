package com.eeibra.ecommerce.inventory.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "inventory")
public class Inventory {
    @Id
    private String id;
    private String productId;
    private Integer quantity;
    private String status; // AVAILABLE, LOW_STOCK, OUT_OF_STOCK
}