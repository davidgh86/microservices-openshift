package com.david.eureka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;
 
    public void send(String topic, User payload) {
        kafkaTemplate.send(topic, payload);
    }
}