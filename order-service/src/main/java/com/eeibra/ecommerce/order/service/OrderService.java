package com.eeibra.ecommerce.order.service;

import com.eeibra.ecommerce.order.model.Order;
import com.eeibra.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        Order savedOrder = orderRepository.save(order);

        // Kafka'ya sipariş oluşturuldu eventi gönder
        kafkaTemplate.send("order-events", savedOrder);

        return savedOrder;
    }

    @KafkaListener(topics = "order-status-events", groupId = "order-group")
    public void handleOrderStatusUpdate(Order updatedOrder) {
        log.info("Received order status update: {}", updatedOrder);

        orderRepository.findById(updatedOrder.getId())
                .ifPresent(order -> {
                    order.setStatus(updatedOrder.getStatus());
                    Order savedOrder = orderRepository.save(order);
                    log.info("Order status updated: {}", savedOrder);
                });
    }

    public java.util.Optional<Order> getOrder(String orderId) {
        return orderRepository.findById(orderId);
    }
}