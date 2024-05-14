package ru.charushnikov.microauthservice.util;

import ru.charushnikov.microauthservice.model.dto.response.TokensDto;
import ru.charushnikov.microauthservice.model.entity.RefreshToken;
import ru.charushnikov.microauthservice.model.entity.Token;

public class TokenUtils {

    public static TokensDto getTokens(Token token, RefreshToken refreshToken) {
        return TokensDto.builder()
                .token(token.getToken())
                .refreshToken(refreshToken.getToken())
                .build();
    }
}
