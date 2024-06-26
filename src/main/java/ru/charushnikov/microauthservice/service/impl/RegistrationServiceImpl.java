package ru.charushnikov.microauthservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.charushnikov.microauthservice.mapper.RegistrationMapper;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.dto.response.RegisterResponseDto;
import ru.charushnikov.microauthservice.model.dto.response.TokensDto;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.RefreshToken;
import ru.charushnikov.microauthservice.model.entity.Token;
import ru.charushnikov.microauthservice.model.entity.UserProfile;
import ru.charushnikov.microauthservice.service.*;
import ru.charushnikov.microauthservice.util.PhoneUtils;
import ru.charushnikov.microauthservice.util.TokenUtils;

@Service
@RequiredArgsConstructor
@Slf4j //TODO сделать логирование
public class RegistrationServiceImpl implements RegistrationService {

    private final ClientService clientService;

    private final UserProfileService userProfileService;

    private final RegistrationMapper registrationMapper;

    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;


    @Override
    @Transactional
    public RegisterResponseDto registerClientInApp(RegisterRequestDto dto) {
        String validatedMobilePhone = PhoneUtils.convertToStandardFormat(dto.getMobilePhone());
        dto.setMobilePhone(validatedMobilePhone);
        Client client = clientService.registerClient(dto);
        UserProfile userProfile = userProfileService.registerUserProfile(dto, client);
        RegisterResponseDto registeredClient = registrationMapper.map(userProfile, client);
        Token token = tokenService.createToken(client);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(client);
        TokensDto tokensDto = TokenUtils.getTokens(token, refreshToken); // наверно лишнее создавть токены при регистрации, лучше делать их при авторизации
        registeredClient.setTokensDto(tokensDto);
        // если есть email - отправить уведомление на почту
        return registeredClient;
    }

}
