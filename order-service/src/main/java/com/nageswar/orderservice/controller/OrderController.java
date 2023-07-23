package com.nageswar.orderservice.controller;

import com.nageswar.orderservice.dto.Payment;
import com.nageswar.orderservice.dto.TransactionRequest;
import com.nageswar.orderservice.dto.TransactionResponse;
import com.nageswar.orderservice.entity.Order;
import com.nageswar.orderservice.service.OrderService;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PostMapping("/bookOrder")
    //@CircuitBreaker(name="payment", fallbackMethod = "fallbackMethod")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request){
        return orderService.saveOrder(request);
    }

    /*public String fallbackMethod(TransactionRequest request, RuntimeException exception){
        return "oops!";
    }*/
}
