package com.example.hw4.orders.scheduler;

import com.example.hw4.orders.entity.Outbox;
import com.example.hw4.orders.repository.OutboxRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OutboxPoller {
    private final OutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;

    public OutboxPoller(OutboxRepository outboxRepository, RabbitTemplate rabbitTemplate) {
        this.outboxRepository = outboxRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelayString = "${outbox.poll-interval}")
    public void pollOutbox() {
        outboxRepository.findBySentFalse().forEach(this::sendMessage);
    }

    @Transactional
    private void sendMessage(Outbox outbox) {
        rabbitTemplate.convertAndSend(outbox.getType(), outbox.getPayload());
        outbox.setSent(true);
        outboxRepository.save(outbox);
    }
}