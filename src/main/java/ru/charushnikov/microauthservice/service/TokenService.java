package ru.charushnikov.microauthservice.service;

import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.Token;

public interface TokenService {
    Token createToken(Client client);
}
