package ru.charushnikov.microauthservice.service;

import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.entity.Client;

public interface ClientService {

    Client registerClient(RegisterRequestDto dto);
}
