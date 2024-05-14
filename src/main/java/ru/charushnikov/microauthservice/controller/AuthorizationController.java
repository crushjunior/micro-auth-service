package ru.charushnikov.microauthservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.charushnikov.microauthservice.model.dto.request.LoginRequest;
import ru.charushnikov.microauthservice.model.dto.response.ErrorMessage;
import ru.charushnikov.microauthservice.model.dto.response.TokensDto;
import ru.charushnikov.microauthservice.service.AuthorizationService;

@RestController
@RequestMapping("/auth-service")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    @Operation(summary = "EP2: Authenticate client", tags = "authorization",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            headers = @Header(name = "Set-Cookie", description = "Required if Is-Web-Request = true. " +
                                    "May not appear in Swagger UI"),
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TokensDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    public ResponseEntity<TokensDto> authenticateUser(@RequestHeader(name = "Is-Web-Request", required = false)
                                              @Parameter(description = "Request from web") boolean isWebRequest,
                                              @Valid @RequestBody LoginRequest authBody, HttpServletResponse response) {
        return ResponseEntity.ok(authorizationService.authenticateUser(isWebRequest, authBody, response));
    }
}
