package ru.charushnikov.microauthservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDto {
    private UUID id;
    private String mobilePhone;
    private String passwordEncoded;
    private String firstName;
    private String lastName;
    private String identificationPassportNumber;
    private String email;
    private LocalDate appRegistrationDate;
    private TokensDto tokensDto;
}
