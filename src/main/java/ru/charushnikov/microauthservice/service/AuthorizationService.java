package ru.charushnikov.microauthservice.service;

import jakarta.servlet.http.HttpServletResponse;
import ru.charushnikov.microauthservice.model.dto.request.LoginRequest;
import ru.charushnikov.microauthservice.model.dto.response.TokensDto;

public interface AuthorizationService {

    TokensDto authenticateUser(boolean isWebRequest, LoginRequest loginRequest, HttpServletResponse response);
}
