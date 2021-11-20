package com.home.kafka.config;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
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
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaAdmin kafkaAdmin(){
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic newTopic(
            @Value("${kafka.topic.name}") String nameTopic,
            @Value("${kafka.topic.num-partitions}") Integer numPartitions,
            @Value("${kafka.topic.replication-factor}") String replicationFactor
    ){
        return TopicBuilder
                .name(nameTopic)
                .partitions(numPartitions)
                .build();
        //return new NewTopic(nameTopic, numPartitions, Short.valueOf(replicationFactor));
    }

}
