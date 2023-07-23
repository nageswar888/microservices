package com.nageswar.orderservice.service;

import com.nageswar.orderservice.dto.Payment;
import com.nageswar.orderservice.dto.TransactionRequest;
import com.nageswar.orderservice.dto.TransactionResponse;
import com.nageswar.orderservice.entity.Order;
import com.nageswar.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    RestTemplate restTemplate;


    public TransactionResponse saveOrder(TransactionRequest request){

        String response = "";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        //rest call
        Payment paymentResponse = restTemplate.postForObject(
                "http://payment-service/payment/doPayment",payment,Payment.class
        );

        response = paymentResponse.getPaymentStatus().equals("success") ? "payment processing is done, order placed": "There is a failure in payment, order is not placed";
        orderRepository.save(order);

        return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),response);
    }
}
