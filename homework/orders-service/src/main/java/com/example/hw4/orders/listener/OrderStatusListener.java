package com.example.hw4.orders.listener;

import com.example.hw4.orders.dto.OrderStatusMessage;
import com.example.hw4.orders.service.OrdersService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusListener {
    private final OrdersService ordersService;

    public OrderStatusListener(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @RabbitListener(queues = "order_paid", containerFactory = "rabbitListenerContainerFactory")
    public void handleOrderPaid(OrderStatusMessage msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            ordersService.processOrderStatus(msg, "PAID");
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
            throw e;
        }
    }

    @RabbitListener(queues = "order_failed", containerFactory = "rabbitListenerContainerFactory")
    public void handleOrderFailed(OrderStatusMessage msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            ordersService.processOrderStatus(msg, "FAILED");
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
            throw e;
        }
    }
}