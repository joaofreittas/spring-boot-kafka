package com.home.kafka.config;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;

@Configuration
public class ProducerKafkaConfig {

    private KafkaProperties kafkaProperties;

    public ProducerKafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(){
        var configs = new HashMap<String, Object>();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public ProducerFactory<String, Object> jsonProducerFactory(){
        var configs = new HashMap<String, Object>();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new JsonSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, Serializable> jsonKafkaTemplate(){ return new KafkaTemplate(jsonProducerFactory()); }

    @Bean
    public KafkaAdmin kafkaAdmin(){
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaAdmin.NewTopics newTopic(
            @Value("${kafka.topic-test.name}") String testNameTopic,
            @Value("${kafka.topic-transaction.name}") String transactionNameTopic,
            @Value("${kafka.topic-test.num-partitions}") Integer testNumPartitions,
            @Value("${kafka.topic-transaction.num-partitions}") Integer transactionNumPartitions,
            @Value("${kafka.topic-test.replication-factor}") String testReplicationFactor,
            @Value("${kafka.topic-transaction.replication-factor}") String transactionReplicationFactor
    ){
//        return TopicBuilder
//                .name(nameTopic)
//                .partitions(numPartitions)
//                .build();
        //return new NewTopic(nameTopic, numPartitions, Short.valueOf(replicationFactor));

        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(testNameTopic).partitions(testNumPartitions).build(),
                TopicBuilder.name(transactionNameTopic).partitions(transactionNumPartitions).build()
        );
    }

}
