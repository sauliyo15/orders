package com.sauliyo15.orders.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sauliyo15.orders.domain.Order;
import com.sauliyo15.orders.repository.OrderRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderConsumer {

    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;


    public OrderConsumer(ObjectMapper objectMapper, OrderRepository orderRepository) {
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrder(ConsumerRecord<String, String> record) {
        try {
            // Convierte el valor del mensaje JSON a un objeto Order
            String jsonValue = record.value();
            Order order = objectMapper.readValue(jsonValue, Order.class);

            // Actualiza el objeto
            order.setValidated(true);
            order.setValidationDate(LocalDate.now());

            // Guarda el objeto en la base de datos
            orderRepository.save(order);

            System.out.println("Order consumida y validada: " + order);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar mensaje de Kafka", e);
        }
    }
}
