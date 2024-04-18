package ru.charushnikov.microauthservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.charushnikov.microauthservice.exception.DuplicatedMailException;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.UserProfile;
import ru.charushnikov.microauthservice.repository.UserProfileRepository;
import ru.charushnikov.microauthservice.service.UserProfileService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final PasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserProfile registerUserProfile(RegisterRequestDto dto, Client client) {
        if (dto.getEmail() != null && userProfileRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicatedMailException("Email is already in use");
        }

        UserProfile userProfile = UserProfile.builder()
                .client(client)
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .appRegistrationDate(LocalDate.now())
                .build();

        return userProfileRepository.save(userProfile);
    }
}
