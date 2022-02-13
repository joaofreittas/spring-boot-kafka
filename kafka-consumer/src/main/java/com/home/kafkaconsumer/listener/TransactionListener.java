package com.home.kafkaconsumer.listener;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.home.kafkaconsumer.custom.TransactionCustomListener;
import com.home.kafkaconsumer.dto.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionListener {

//  @KafkaListener(
//          topics = "${spring.kafka.topic-transaction.name}",
//          groupId = "${spring.kafka.group.id}",
//          containerFactory = "transactionKafkaListenerContainerFactory"
//  )
  @TransactionCustomListener(groupId = "group-1")
  public void listen(TransactionDTO transactionDTO, ConsumerRecord consumerRecord) {
    System.out.println("--> Thread Id: " + Thread.currentThread().getId());

    System.out.println("-> TOPIC: " + consumerRecord.topic());
    System.out.println("-> PARTITION_ID: " + consumerRecord.partition());
    System.out.println("-> KEY: " + consumerRecord.key());
    System.out.println("-> OFFSET: " + consumerRecord.offset());
    System.out.println("-> MESSAGE: " + consumerRecord.value());
    System.out.println("-> DTO: " + transactionDTO);
  }

}
