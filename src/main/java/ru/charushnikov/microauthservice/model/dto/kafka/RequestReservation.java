package ru.charushnikov.microauthservice.model.dto.kafka;

import lombok.*;

import java.util.UUID;

@Builder
public record RequestReservation(
    UUID clientId,
    Long productId,
    Integer amount
){}
