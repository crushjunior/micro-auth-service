package ru.charushnikov.microauthservice.kafka.publisher.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import ru.charushnikov.microauthservice.kafka.publisher.MessagePublisher;
import ru.charushnikov.microauthservice.model.dto.kafka.RequestReservation;

import java.util.concurrent.CompletableFuture;

/**
 *
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationPublisher implements MessagePublisher<RequestReservation> {

    @Value("${kafka.topics.shop-service.request_reservation}")
    private String topicName;
    private final KafkaTemplate<String, RequestReservation> kafkaTemplate;

    @Override
    public void publish(RequestReservation message) {
        CompletableFuture<SendResult<String, RequestReservation>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if(ex==null) {
                log.info("Sent message to topic [" + topicName +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }else {
                log.error("Unable to send message to topic [" + topicName + "] due to : " + ex.getMessage());
            }
        });
    }
}
