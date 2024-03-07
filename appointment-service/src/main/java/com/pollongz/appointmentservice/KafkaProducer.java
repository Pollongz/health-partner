package com.pollongz.appointmentservice;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message, String topicName) {
        kafkaTemplate.send(topicName, message);
        System.out.println("Message about new available visit has been successfully sent to the subscribers: " + topicName);
    }
}
