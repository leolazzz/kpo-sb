package com.example.hw4.payments.listener;

import com.example.hw4.payments.dto.PayOrderMessage;
import com.example.hw4.payments.service.PaymentsService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class PayOrderListener {
    private final PaymentsService paymentsService;

    public PayOrderListener(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @RabbitListener(queues = "pay_order", containerFactory = "rabbitListenerContainerFactory")
    public void handlePayOrder(PayOrderMessage msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            paymentsService.processPayOrder(msg);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
            throw e;
        }
    }
}