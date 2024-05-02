package ru.charushnikov.microauthservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.charushnikov.microauthservice.mapper.impl.RegistrationMapperImpl;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.dto.response.RegisterResponseDto;
import ru.charushnikov.microauthservice.model.dto.response.TokensDto;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.RefreshToken;
import ru.charushnikov.microauthservice.model.entity.Token;
import ru.charushnikov.microauthservice.model.entity.UserProfile;
import ru.charushnikov.microauthservice.service.impl.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {
    private static final String JWS = "eyJhbGciOiJIUzUxMiJ9.eyJjbGllbnRJZCI6ImM5NWU4NmViLWM3ZmYtNGY3OS05ZjY1LTVjYjYzMjEzOTNiMCIsInN1YiI6Ik9FR0ZFRUhMWkUyRiIsImlhdCI6MTY2ODcwNTQ4MywiZXhwIjoxNjY4NzA5MDgzfQ.--PwESRU9iU_smpIDGjWOSoMqtJD3AtaNbn6qQ3MYHETNPg8wlLKIAIfGoNHwTHamHfDu0B1E9oE_O_Qj6Rtbw";

    private static final Date date = new Date(new Date().getTime());
    @Mock
    private ClientServiceImpl clientService;

    @Mock
    private UserProfileServiceImpl userProfileService;

    @Mock
    private RegistrationMapperImpl registrationMapper;

    @Mock
    private TokenServiceImpl tokenService;
    @Mock
    private RefreshTokenServiceImpl refreshTokenService;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    private RegisterRequestDto requestDto;

    private RegisterResponseDto responseDto;

    private Client client;

    private UserProfile userProfile;

    private Token token;

    private RefreshToken refreshToken;

    @BeforeEach
    public void setup() {
        requestDto = getRegisterRequestDto();
        client = getClient();
        userProfile = getUserProfile();

        token = Token.builder()
                .expiryDate(date.toInstant())
                .token(JWS)
                .client(client)
                .build();

        refreshToken = RefreshToken.builder()
                .expiryDate(date.toInstant())
                .token(JWS)
                .client(client)
                .build();

        TokensDto tokensDto = TokensDto.builder()
                .token(token.getToken())
                .refreshToken(refreshToken.getToken())
                .build();

        responseDto = getRegisterResponseDto();
        responseDto.setTokensDto(tokensDto);

    }

    @Test
    public void registerClientInApp() {
        doReturn(client).when(clientService).registerClient(any(RegisterRequestDto.class));
        doReturn(userProfile).when(userProfileService).registerUserProfile(any(RegisterRequestDto.class), any(Client.class));
        doReturn(responseDto).when(registrationMapper).map(any(UserProfile.class), any(Client.class));
        when(tokenService.createToken(client)).thenReturn(token);
        when(refreshTokenService.createRefreshToken(client)).thenReturn(refreshToken);
        RegisterResponseDto returnedDto = registrationService.registerClientInApp(requestDto);
        verify(clientService, atLeastOnce()).registerClient(any(RegisterRequestDto.class));
        verify(userProfileService, atLeastOnce()).registerUserProfile(any(RegisterRequestDto.class), any(Client.class));
        verify(registrationMapper, atLeastOnce()).map(any(UserProfile.class), any(Client.class));
        verify(tokenService, atLeastOnce()).createToken(any(Client.class));
        verify(refreshTokenService, atLeastOnce()).createRefreshToken(any(Client.class));
        assertEquals(returnedDto, responseDto);
    }
}
