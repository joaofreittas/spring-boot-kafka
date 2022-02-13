package com.home.kafka.controller;

import com.home.kafka.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

  private KafkaTemplate<String, Serializable> jsonKafkaTemplate;
  private String TOPIC_NAME;

  public TransactionController(KafkaTemplate<String, Serializable> kafkaTemplate, @Value("${kafka.topic-transaction.name}") String TOPIC_NAME) {
    this.jsonKafkaTemplate = kafkaTemplate;
    this.TOPIC_NAME = TOPIC_NAME;
  }

  @PostMapping
  public ResponseEntity<?> sendTransaction(@RequestBody TransactionDTO transactionDTO) {
    jsonKafkaTemplate.send(TOPIC_NAME, transactionDTO);
    return ResponseEntity.ok().build();
  }


}
