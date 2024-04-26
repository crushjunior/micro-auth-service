package ru.charushnikov.microauthservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.charushnikov.microauthservice.exception.DuplicatePassportException;
import ru.charushnikov.microauthservice.exception.DuplicatedMailException;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.entity.UserProfile;
import ru.charushnikov.microauthservice.repository.UserProfileRepository;
import ru.charushnikov.microauthservice.service.impl.UserProfileServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static reactor.core.publisher.Mono.when;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getRegisterRequestDto;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getUserProfile;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    private UserProfile userProfile;

    private RegisterRequestDto requestDto;


    @BeforeEach
    public void setup() {
        userProfile = getUserProfile();
        requestDto = getRegisterRequestDto();
    }

    @Test
    public void whenUserProfileSave_isSuccessful() {
        doReturn(Boolean.FALSE).when(userProfileRepository).existsByEmail(anyString());
        doReturn(userProfile).when(userProfileRepository).save(any(UserProfile.class));
        doReturn(userProfile.getPassword()).when(encoder).encode(anyString());
        UserProfile savedProfile = userProfileService.registerUserProfile(requestDto, userProfile.getClient());
        assertEquals(savedProfile, userProfile);
    }

    @Test
    public void whenUserProfileSaveFailed_thenExceptionThrows() {
        doReturn(Boolean.TRUE).when(userProfileRepository).existsByEmail(anyString());
        assertThrows(DuplicatedMailException.class, () -> userProfileService.registerUserProfile(requestDto, userProfile.getClient()));
    }
}
