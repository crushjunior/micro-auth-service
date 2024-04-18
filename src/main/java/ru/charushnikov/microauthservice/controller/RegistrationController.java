package ru.charushnikov.microauthservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.dto.response.RegisterResponseDto;
import ru.charushnikov.microauthservice.service.RegistrationService;

@RestController
@RequestMapping("/auth-service/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/user-profile/new")
//    @Operation(summary = "EP1: Register client in application", tags = "registration",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Success",
//                            headers = @Header(name = "Set-Cookie",
//                                    description = "Required if Is-Web-Request = true. May not appear in Swagger UI"),
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    schema = @Schema(implementation = RegisterResponseClientDto.class))),
//                    @ApiResponse(responseCode = "400", description = "Bad request",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    schema = @Schema(implementation = ErrorMessage.class)))
//            })
    public ResponseEntity<RegisterResponseDto> registerClientInApp(@Valid @RequestBody RegisterRequestDto dto) {
        RegisterResponseDto registerRequestClientDto = registrationService.registerClientInApp(dto);
        return ResponseEntity.ok(registerRequestClientDto);
    }
}
