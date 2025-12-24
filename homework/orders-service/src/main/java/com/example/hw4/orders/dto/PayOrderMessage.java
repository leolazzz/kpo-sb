package com.example.hw4.orders.dto;

import java.math.BigDecimal;

public class PayOrderMessage {
    private String messageId;
    private Long orderId;
    private String userId;
    private BigDecimal amount;

    public PayOrderMessage() {}

    public PayOrderMessage(String messageId, Long orderId, String userId, BigDecimal amount) {
        this.messageId = messageId;
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}