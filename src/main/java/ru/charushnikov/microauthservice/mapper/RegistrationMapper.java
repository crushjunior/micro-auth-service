package ru.charushnikov.microauthservice.mapper;

import ru.charushnikov.microauthservice.model.dto.response.RegisterResponseDto;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.UserProfile;

public interface RegistrationMapper {
    RegisterResponseDto map(UserProfile userProfile, Client client);
}
