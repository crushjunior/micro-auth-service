package ru.charushnikov.microauthservice.service;

import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.dto.response.RegisterResponseDto;

public interface RegistrationService {

    RegisterResponseDto registerClientInApp(RegisterRequestDto dto);
}
