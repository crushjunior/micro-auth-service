package ru.charushnikov.microauthservice.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.charushnikov.microauthservice.exception.IncorrectPasswordOrLoginException;
import ru.charushnikov.microauthservice.model.dto.request.LoginRequest;
import ru.charushnikov.microauthservice.model.dto.response.TokensDto;
import ru.charushnikov.microauthservice.model.entity.RefreshToken;
import ru.charushnikov.microauthservice.model.entity.Token;
import ru.charushnikov.microauthservice.model.entity.UserProfile;
import ru.charushnikov.microauthservice.service.AuthorizationService;
import ru.charushnikov.microauthservice.service.RefreshTokenService;
import ru.charushnikov.microauthservice.service.TokenService;
import ru.charushnikov.microauthservice.service.UserProfileService;
import ru.charushnikov.microauthservice.util.TokenUtils;
import ru.charushnikov.microauthservice.util.WebRequestUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserProfileService userProfileService;

    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;


    @Override
    @Transactional
    public TokensDto authenticateUser(boolean isWebRequest, LoginRequest loginRequest, HttpServletResponse response) {
        log.info("User with mobile: {} authentication is in progress", loginRequest.mobilePhone());
        UserProfile userProfile = userProfileService.findUserProfileByMobilePhone(loginRequest.mobilePhone());
        checkPasswordEquality(loginRequest.password(), userProfile);
        log.info("A token will be created for userProfile: {} with mobilePhone: {}",
                userProfile.getId(), loginRequest.mobilePhone());

        Token token = tokenService.createToken(userProfile.getClient());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userProfile.getClient());
        TokensDto tokensDto = TokenUtils.getTokens(token, refreshToken);

        if (isWebRequest) {
            WebRequestUtils.setRefreshTokenCookie(response, refreshToken.getToken());
        }

        return tokensDto;
    }

    private void checkPasswordEquality(String password, UserProfile userProfile) {
        if (!BCrypt.checkpw(password, userProfile.getPassword())) {
            throw new IncorrectPasswordOrLoginException("Incorrect password or login");
        }
    }
}
