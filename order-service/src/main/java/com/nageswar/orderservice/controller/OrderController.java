package com.nageswar.orderservice.controller;

import com.nageswar.orderservice.dto.Payment;
import com.nageswar.orderservice.dto.TransactionRequest;
import com.nageswar.orderservice.dto.TransactionResponse;
import com.nageswar.orderservice.entity.Order;
import com.nageswar.orderservice.service.OrderService;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name="inventory", fallbackMethod = "sample")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request){
        return orderService.saveOrder(request);
    }

    public TransactionResponse sample(Exception e){
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setMessage("Error while placing the order. Please try again later");
        return transactionResponse;
    }
}
