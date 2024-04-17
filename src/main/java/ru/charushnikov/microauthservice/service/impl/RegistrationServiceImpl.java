package ru.charushnikov.microauthservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.dto.response.RegisterResponseDto;
import ru.charushnikov.microauthservice.repository.UserProfileRepository;
import ru.charushnikov.microauthservice.service.RegistrationService;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserProfileRepository userProfileRepository;


    @Override
    public RegisterResponseDto registerClientInApp(RegisterRequestDto dto) {
        return null;
    }
}
