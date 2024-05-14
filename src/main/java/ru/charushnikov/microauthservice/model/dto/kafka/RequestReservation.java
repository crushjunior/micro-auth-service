package ru.charushnikov.microauthservice.model.dto.kafka;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestReservation {
    private UUID clientId;
    private Long productId;
    private Integer amount;
}
