package com.example.hw4.orders.dto;

public class OrderUpdate {
    private String status;

    public OrderUpdate(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}