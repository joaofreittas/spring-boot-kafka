package com.home.kafka.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/message")
public class MessageController {

    private KafkaTemplate<String, String> kafkaTemplate;
    private String TOPIC_NAME;

    public MessageController(KafkaTemplate<String, String> kafkaTemplate, @Value("${kafka.topic.name}") String nameTopic) {
        this.TOPIC_NAME = nameTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<Object> send(){
        String message = "Message content send at (" + LocalDate.now() +")";
        kafkaTemplate.send(TOPIC_NAME,"Message: " + message);
//        kafkaTemplate.send(TOPIC_NAME,"my-key", "Número: " + message);

        return ResponseEntity.ok().build();
    }
}