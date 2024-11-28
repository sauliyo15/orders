package com.sauliyo15.orders.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sauliyo15.orders.domain.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;


    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendOrder(Order order) {
        try {
            // Convierte el objeto Order a JSON string
            String orderJson = objectMapper.writeValueAsString(order);

            // Envía al topic por defecto
            kafkaTemplate.sendDefault(orderJson);

            System.out.println("Order enviada al topic: " + defaultTopic + ", contenido: " + orderJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir Order a JSON", e);
        }
    }
}
