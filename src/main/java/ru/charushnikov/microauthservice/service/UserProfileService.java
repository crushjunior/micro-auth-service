package ru.charushnikov.microauthservice.service;

import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.UserProfile;

public interface UserProfileService {

    UserProfile registerUserProfile(RegisterRequestDto dto, Client client);
}
