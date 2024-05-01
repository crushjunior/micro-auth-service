package ru.charushnikov.microauthservice.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.RefreshToken;
import ru.charushnikov.microauthservice.repository.RefreshTokenRepository;
import ru.charushnikov.microauthservice.service.RefreshTokenService;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${micro.app.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    @Value("${micro.app.jwtSecret}")
    private String jwtSecret;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(Client client) {
        RefreshToken refreshToken = RefreshToken.builder()
                .client(client)
                .expiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs))
                .token(Jwts.builder().setSubject(client.getPassportData().getIdentificationPassportNumber())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
                        .signWith(SignatureAlgorithm.HS512, jwtSecret).compact())
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
}
