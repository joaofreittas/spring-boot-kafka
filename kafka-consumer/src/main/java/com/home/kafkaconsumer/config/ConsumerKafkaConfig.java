package com.home.kafkaconsumer.config;

import com.home.kafkaconsumer.dto.TransactionDTO;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@EnableKafka
@Configuration
public class ConsumerKafkaConfig {

    private KafkaProperties kafkaProperties;

    public ConsumerKafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        var configs = new HashMap<String, Object>();
//        configs.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
//        configs.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configs.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//        return new DefaultKafkaConsumerFactory<>(configs);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
//        factory.setConsumerFactory(consumerFactory());
//
//        return factory;
//    }

//    @Bean
//    public ConsumerFactory<String, TransactionDTO> transactionConsumerFactory() {
//        var configs = new HashMap<String, Object>();
//        configs.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
//        configs.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configs.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//
//        var jsonDeserializer = new JsonDeserializer<>(TransactionDTO.class)
//                .trustedPackages("*")
//                .forKeys();
//
//        return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), jsonDeserializer);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, TransactionDTO> transactionKafkaListenerContainerFactory() {
//        var factory = new ConcurrentKafkaListenerContainerFactory<String, TransactionDTO>();
//        factory.setConsumerFactory(transactionConsumerFactory());
//
//        return factory;
//    }


    @Bean
    public ConsumerFactory jsonConsumerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory jsonKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(jsonConsumerFactory());
        factory.setMessageConverter(new JsonMessageConverter());
        return factory;
    }



}
