package ru.charushnikov.microauthservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.charushnikov.microauthservice.kafka.publisher.MessagePublisher;
import ru.charushnikov.microauthservice.model.dto.kafka.RequestReservation;

@RestController
@RequestMapping("/auth-service/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final MessagePublisher messagePublisher;

    @PostMapping("/reservation")
    public ResponseEntity<String> sendRequestReservation(@RequestBody RequestReservation requestReservation) {
        messagePublisher.publish(requestReservation);
        return new ResponseEntity<>("Message sent to Kafka topic", HttpStatus.OK);
    }
}
