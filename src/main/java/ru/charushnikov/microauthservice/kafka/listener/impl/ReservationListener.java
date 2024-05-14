package ru.charushnikov.microauthservice.kafka.listener.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.charushnikov.microauthservice.kafka.listener.MessageListener;
import ru.charushnikov.microauthservice.model.dto.kafka.ResponseReservation;

/**
 *
 */

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topics.shop-service.response_reservation}",
        groupId = "${spring.kafka.consumer.group-id}")
public class ReservationListener implements MessageListener<ResponseReservation> {

    @Override
    @KafkaHandler
    @KafkaListener(topics ="${kafka.topics.shop-service.response_reservation}")
            public void listenMessage(ResponseReservation reservationDto) {
        log.info(reservationDto.message());
    }
}
