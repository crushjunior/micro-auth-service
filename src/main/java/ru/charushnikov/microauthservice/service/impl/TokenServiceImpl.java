package ru.charushnikov.microauthservice.service.impl;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.Token;
import ru.charushnikov.microauthservice.repository.TokenRepository;
import ru.charushnikov.microauthservice.service.TokenService;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public Token createToken(Client client) {
        return null;
    }
}
