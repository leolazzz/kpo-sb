package com.example.hw4.orders.controller;

import com.example.hw4.orders.dto.CreateOrderDto;
import com.example.hw4.orders.entity.OrderEntity;
import com.example.hw4.orders.service.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    public OrderEntity createOrder(@RequestBody CreateOrderDto dto, @RequestHeader("user-id") String userId) {
        return ordersService.createOrder(userId, dto);
    }

    @GetMapping
    public List<OrderEntity> listOrders(@RequestHeader("user-id") String userId) {
        return ordersService.listOrders(userId);
    }

    @GetMapping("/{id}")
    public OrderEntity getOrder(@PathVariable Long id, @RequestHeader("user-id") String userId) {
        return ordersService.getOrder(id, userId);
    }
}