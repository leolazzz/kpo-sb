package com.example.hw4.payments.dto;

public class OrderStatusMessage {
    private String messageId;
    private Long orderId;

    public OrderStatusMessage() {}

    public OrderStatusMessage(String messageId, Long orderId) {
        this.messageId = messageId;
        this.orderId = orderId;
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
}