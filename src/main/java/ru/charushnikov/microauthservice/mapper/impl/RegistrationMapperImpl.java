package ru.charushnikov.microauthservice.mapper.impl;

import org.springframework.stereotype.Component;
import ru.charushnikov.microauthservice.mapper.RegistrationMapper;
import ru.charushnikov.microauthservice.model.dto.response.RegisterResponseDto;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.UserProfile;

@Component
public class RegistrationMapperImpl implements RegistrationMapper {
    @Override
    public RegisterResponseDto map(UserProfile userProfile, Client client) {
        return RegisterResponseDto.builder()
                .id(userProfile.getId())
                .mobilePhone(client.getMobilePhone())
                .passwordEncoded(userProfile.getPassword())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .identificationPassportNumber(client.getPassportData().getIdentificationPassportNumber())
                .email(userProfile.getEmail())
                .appRegistrationDate(userProfile.getAppRegistrationDate())
                .build();
    }
}
