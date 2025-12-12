package com.example.hw4.payments.service;

import com.example.hw4.payments.dto.OrderStatusMessage;
import com.example.hw4.payments.dto.PayOrderMessage;
import com.example.hw4.payments.entity.Account;
import com.example.hw4.payments.entity.Outbox;
import com.example.hw4.payments.entity.ProcessedMessage;
import com.example.hw4.payments.repository.AccountRepository;
import com.example.hw4.payments.repository.OutboxRepository;
import com.example.hw4.payments.repository.ProcessedMessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentsService {

    private final AccountRepository accountRepository;
    private final ProcessedMessageRepository processedMessageRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void createAccount(String userId) {
        Optional<Account> existingAccount = accountRepository.findById(userId);
        if (existingAccount.isPresent()) {
            throw new RuntimeException("Account already exists for user: " + userId);
        }

        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(BigDecimal.ZERO);
        accountRepository.save(account);

        log.info("Account created for user: {}", userId);
    }

    @Transactional
    public void topUp(String userId, BigDecimal amount) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Account not found for user: " + userId));

        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);

        log.info("Topped up account for user: {} by {}", userId, amount);
    }

    public BigDecimal getBalance(String userId) {
        return accountRepository.findById(userId)
                .map(Account::getBalance)
                .orElseThrow(() -> new RuntimeException("Account not found for user: " + userId));
    }

    @Transactional
    public void processPayOrder(PayOrderMessage msg) {
        if (processedMessageRepository.existsById(msg.getMessageId())) {
            log.info("Message already processed: {}", msg.getMessageId());
            return;
        }

        try {
            Account account = accountRepository.findById(msg.getUserId())
                    .orElseThrow(() -> new RuntimeException("Account not found for user: " + msg.getUserId()));

            boolean success = false;
            String eventType;

            if (account.getBalance().compareTo(msg.getAmount()) >= 0) {
                BigDecimal newBalance = account.getBalance().subtract(msg.getAmount());
                account.setBalance(newBalance);
                accountRepository.save(account);

                success = true;
                eventType = "order_paid";
                log.info("Payment successful for order: {}, amount: {}", msg.getOrderId(), msg.getAmount());
            } else {
                eventType = "order_failed";
                log.info("Payment failed for order: {} - insufficient funds", msg.getOrderId());
            }

            OrderStatusMessage statusMsg = new OrderStatusMessage(
                    UUID.randomUUID().toString(),
                    msg.getOrderId()
            );

            String payload = objectMapper.writeValueAsString(statusMsg);
            Outbox outbox = new Outbox(eventType, payload);
            outboxRepository.save(outbox);

            ProcessedMessage processedMessage = new ProcessedMessage(msg.getMessageId());
            processedMessageRepository.save(processedMessage);

        } catch (Exception e) {
            log.error("Error processing payment for order: {}", msg.getOrderId(), e);
            throw new RuntimeException("Payment processing failed", e);
        }
    }
}