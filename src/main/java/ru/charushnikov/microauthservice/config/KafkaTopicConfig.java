package ru.charushnikov.microauthservice.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${kafka.topics.shop-service.request_reservation}")
    private String requestReservationTopicName;

    @Value("${kafka.topics.shop-service.response_reservation}")
    private String responseReservationTopicName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic requestReservationTopic() {
        return TopicBuilder.name(requestReservationTopicName)
                .build();
    }

    @Bean
    public NewTopic responseReservationTopic() {
        return TopicBuilder.name(responseReservationTopicName)
                .build();
    }
}
