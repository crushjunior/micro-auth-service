package ru.charushnikov.microauthservice.service;

import ru.charushnikov.microauthservice.model.entity.RefreshToken;

import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(UUID clientId);
}
