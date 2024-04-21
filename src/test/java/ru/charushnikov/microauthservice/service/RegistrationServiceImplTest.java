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
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.UserProfile;
import ru.charushnikov.microauthservice.service.impl.ClientServiceImpl;
import ru.charushnikov.microauthservice.service.impl.RegistrationServiceImpl;
import ru.charushnikov.microauthservice.service.impl.UserProfileServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {
    @Mock
    private ClientServiceImpl clientService;

    @Mock
    private UserProfileServiceImpl userProfileService;

    @Mock
    private RegistrationMapperImpl registrationMapper;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    private RegisterRequestDto requestDto;

    private RegisterResponseDto responseDto;

    private Client client;

    private UserProfile userProfile;

    @BeforeEach
    public void setup() {
        requestDto = getRegisterRequestDto();
        responseDto = getRegisterResponseDto();
        client = getClient();
        userProfile = getUserProfile();
    }

    @Test
    public void registerClientInApp() {
        doReturn(client).when(clientService).registerClient(any(RegisterRequestDto.class));
        doReturn(userProfile).when(userProfileService).registerUserProfile(any(RegisterRequestDto.class), any(Client.class));
        doReturn(responseDto).when(registrationMapper).map(any(UserProfile.class), any(Client.class));
        RegisterResponseDto returnedDto = registrationService.registerClientInApp(requestDto);
        verify(clientService, atLeastOnce()).registerClient(any(RegisterRequestDto.class));
        verify(userProfileService, atLeastOnce()).registerUserProfile(any(RegisterRequestDto.class), any(Client.class));
        verify(registrationMapper, atLeastOnce()).map(any(UserProfile.class), any(Client.class));
        assertEquals(returnedDto, responseDto);
    }
}
