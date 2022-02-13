package com.home.kafkaconsumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

    @KafkaListener(topics = "${spring.kafka.topic-test.name}", groupId = "${spring.kafka.group.id}")
    public void listen(ConsumerRecord consumerRecord) {
        System.out.println("--> Thread Id: " + Thread.currentThread().getId());

        System.out.println("-> TOPIC: " + consumerRecord.topic());
        System.out.println("-> PARTITION_ID: " + consumerRecord.partition());
        System.out.println("-> KEY: " + consumerRecord.key());
        System.out.println("-> OFFSET: " + consumerRecord.offset());
        System.out.println("-> MESSAGE: " + consumerRecord.value());
    }
}
