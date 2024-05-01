package ru.charushnikov.microauthservice.service;

import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Client client);
}
