package ru.charushnikov.microauthservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.service.impl.RegistrationServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getRegisterRequestDto;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @Mock
    private RegistrationServiceImpl registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    private MockMvc mockMvc;

    private RegisterRequestDto request;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        request = getRegisterRequestDto();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders
                .standaloneSetup(registrationController)
                .build();
    }


    @Test
    @DisplayName("Registration client in app. Status Ok. Success")
    void registrationClientInApp_IsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth-service/registration/user-profile/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Registration client and create his UserProfile . Status Bad Request. Negative")
    void registerClientAndUserProfile_IsNegative() throws Exception {
        RegisterRequestDto badRequest = new RegisterRequestDto(null,
                null, null, null,
                null, null, null, null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/auth-service/registration/user-profile/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(badRequest));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }
}
