package com.sauliyo15.orders.controller;

import com.sauliyo15.orders.domain.Order;
import com.sauliyo15.orders.kafka.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;


    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        try {
            orderProducer.sendOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order sent to Kafka successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending order to Kafka: " + e.getMessage());
        }
    }

}
