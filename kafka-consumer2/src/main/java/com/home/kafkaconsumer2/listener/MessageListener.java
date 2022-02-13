package com.home.kafkaconsumer2.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {


  @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.group.id}")
  public void listener(ConsumerRecord consumerRecord) {
    System.out.println("--> Thread Id: " + Thread.currentThread().getId());

    System.out.println("-> TOPIC: " + consumerRecord.topic());
    System.out.println("-> PARTITION_ID: " + consumerRecord.partition());
    System.out.println("-> KEY: " + consumerRecord.key());
    System.out.println("-> OFFSET: " + consumerRecord.offset());
    System.out.println("-> MESSAGE: " + consumerRecord.value());
  }

}
