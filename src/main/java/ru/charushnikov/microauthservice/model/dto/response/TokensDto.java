package ru.charushnikov.microauthservice.model.dto.response;

import lombok.Builder;

@Builder
public record TokensDto(String token, String refreshToken) {
}
