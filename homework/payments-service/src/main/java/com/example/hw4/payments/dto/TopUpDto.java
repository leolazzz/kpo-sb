package com.example.hw4.payments.dto;

import java.math.BigDecimal;

public class TopUpDto {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}