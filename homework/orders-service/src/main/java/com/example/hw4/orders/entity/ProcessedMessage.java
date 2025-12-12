package com.example.hw4.orders.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProcessedMessage {
    @Id
    private String messageId;

    public ProcessedMessage() {}

    public ProcessedMessage(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}