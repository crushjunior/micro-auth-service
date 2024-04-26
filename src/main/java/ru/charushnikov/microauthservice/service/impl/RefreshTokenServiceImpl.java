package ru.charushnikov.microauthservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.charushnikov.microauthservice.model.entity.RefreshToken;
import ru.charushnikov.microauthservice.repository.RefreshTokenRepository;
import ru.charushnikov.microauthservice.service.RefreshTokenService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(UUID clientId) {
        return null;
    }
}
