package com.david.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class Receiver {

    @Autowired
    EmailService emailService;

    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "${spring.kafka.topic.userCreated}")
    public void receive(User payload) {
        emailService.sendSimpleMessage(payload);
        latch.countDown();
    }

}
