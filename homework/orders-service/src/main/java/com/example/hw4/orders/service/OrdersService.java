package com.example.hw4.orders.service;

import com.example.hw4.orders.dto.CreateOrderDto;
import com.example.hw4.orders.dto.OrderStatusMessage;
import com.example.hw4.orders.dto.OrderUpdate;
import com.example.hw4.orders.dto.PayOrderMessage;
import com.example.hw4.orders.entity.OrderEntity;
import com.example.hw4.orders.entity.Outbox;
import com.example.hw4.orders.entity.ProcessedMessage;
import com.example.hw4.orders.repository.OrderRepository;
import com.example.hw4.orders.repository.OutboxRepository;
import com.example.hw4.orders.repository.ProcessedMessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ProcessedMessageRepository processedMessageRepository;
    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public OrderEntity createOrder(String userId, CreateOrderDto dto) {
        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setDescription(dto.getDescription());
        order.setAmount(dto.getAmount());
        order.setStatus("CREATED");

        OrderEntity savedOrder = orderRepository.save(order);

        log.info("Order created: {} for user: {}", savedOrder.getId(), userId);

        try {
            PayOrderMessage payOrderMessage = new PayOrderMessage(
                    UUID.randomUUID().toString(),
                    savedOrder.getId(),
                    userId,
                    dto.getAmount()
            );

            String payload = objectMapper.writeValueAsString(payOrderMessage);
            Outbox outbox = new Outbox("pay_order", payload);
            outboxRepository.save(outbox);

            log.info("Outbox message created for order: {}", savedOrder.getId());
        } catch (Exception e) {
            log.error("Error creating outbox message for order: {}", savedOrder.getId(), e);
            throw new RuntimeException("Failed to create payment request", e);
        }

        return savedOrder;
    }

    public List<OrderEntity> listOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public OrderEntity getOrder(Long id, String userId) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to order: " + id);
        }

        return order;
    }

    @Transactional
    public void processOrderStatus(OrderStatusMessage msg, String newStatus) {

        if (processedMessageRepository.existsById(msg.getMessageId())) {
            log.info("Order status message already processed: {}", msg.getMessageId());
            return;
        }

        OrderEntity order = orderRepository.findById(msg.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found: " + msg.getOrderId()));

        order.setStatus(newStatus);
        orderRepository.save(order);

        log.info("Order status updated: {} -> {}", msg.getOrderId(), newStatus);


        try {
            OrderUpdate update = new OrderUpdate(newStatus);
            messagingTemplate.convertAndSend("/topic/order/" + order.getId(), update);
            log.info("WebSocket notification sent for order: {}", order.getId());
        } catch (Exception e) {
            log.error("Failed to send WebSocket notification for order: {}", order.getId(), e);
        }

        ProcessedMessage processedMessage = new ProcessedMessage(msg.getMessageId());
        processedMessageRepository.save(processedMessage);
    }
}