package com.home.kafkaconsumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

    @KafkaListener(topics = "topic-1", groupId = "group-1")
    public void listen(ConsumerRecord consumerRecord) {
        System.out.println("-> TOPIC: " + consumerRecord.topic());
        System.out.println("-> PARTITION_ID: " + consumerRecord.partition());
        System.out.println("-> KEY: " + consumerRecord.key());
        System.out.println("-> MESSAGE: " + consumerRecord.value());
    }
}
