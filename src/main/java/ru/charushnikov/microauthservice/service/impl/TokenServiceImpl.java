package ru.charushnikov.microauthservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.Token;
import ru.charushnikov.microauthservice.repository.TokenRepository;
import ru.charushnikov.microauthservice.service.TokenService;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${micro.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${micro.app.jwtSecret}")
    private String jwtSecret;

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public Token createToken(Client client) {
        UUID clientId = client.getId();
        Claims claims = Jwts.claims();
        claims.put("clientId", clientId);

        Date date = new Date((new Date()).getTime() + jwtExpirationMs);

        String jws = Jwts.builder()
                .setClaims(claims)
                .setSubject(client.getMobilePhone())
                .setIssuedAt(new Date())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        Token token = Token.builder()
                .token(jws)
                .expiryDate(date.toInstant())
                .client(client)
                .build();

        Optional<Token> tokenExist = tokenRepository.findByClientId(clientId);
        if (tokenExist.isPresent()) {
            tokenRepository.updateToken(jws, tokenExist.get().getId());
        } else
            token = tokenRepository.save(token);
        return token;
    }
}
