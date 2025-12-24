package com.example.hw4.payments.controller;

import com.example.hw4.payments.dto.CreateAccountDto;
import com.example.hw4.payments.dto.TopUpDto;
import com.example.hw4.payments.service.PaymentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
public class PaymentsController {
    private final PaymentsService paymentsService;

    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountDto dto, @RequestHeader("user-id") String userId) {
        paymentsService.createAccount(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accounts/topup")
    public ResponseEntity<Void> topUp(@RequestBody TopUpDto dto, @RequestHeader("user-id") String userId) {
        paymentsService.topUp(userId, dto.getAmount());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/accounts/balance")
    public BigDecimal getBalance(@RequestHeader("user-id") String userId) {
        return paymentsService.getBalance(userId);
    }
}