package com.eeibra.ecommerce.notification.service;

import com.eeibra.ecommerce.notification.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderEventConsumer {

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void handleOrderEvent(Order order) {
        log.info("Received order event: {}", order);
        // Burada bildirim gönderme işlemleri yapılabilir
        // Email, SMS, Push Notification vb.
    }
}