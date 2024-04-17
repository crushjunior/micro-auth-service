package ru.charushnikov.microauthservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import ru.charushnikov.microauthservice.util.PhoneUtils;

import java.time.LocalDate;

public record RegisterRequestDto(
        @NotNull(message = "{validation.passportNumber.message}")
        @Pattern(regexp = "^\\d{10}$", message = "{validation.passportNumber.message}")
        String identificationPassportNumber,

        @NotNull(message = "{validation.passportIssuanceDate.message}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate issuanceDate,

        @NotNull(message = "{validation.passportBirthDate.message}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthDate,

        @NotNull(message = "{validation.firstName.message}")
        @Pattern(regexp = "^(?=^.{1,30}$)[А-ЯЁA-Z](([-'\\sА-ЯЁA-Zа-яёa-z])*[А-ЯЁA-Zа-яёa-z])?$",
                message = "{validation.firstName.message}")
        String firstName,

        @NotNull(message = "{validation.lastName.message}")
        @Pattern(regexp = "^(?=^.{1,30}$)[А-ЯЁA-Z](([-'\\sА-ЯЁA-Zа-яёa-z])*[А-ЯЁA-Zа-яёa-z])?$",
                message = "{validation.lastName.message}")
        String lastName,

        @NotNull(message = "{validation.phone.message}")
        @Pattern(regexp = PhoneUtils.PHONE_REGEX, message = "{validation.phone.message}")
        String mobilePhone,

        @NotNull(message = "{validation.password.message}")
        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*[!#$%&'()*+,\\-./:;<=>\\[?@\\]^_`{|}~])" +
                "[a-zA-Z0-9!#$%&'()*+,\\-./:;<=>\\[?@\\]^_`{|}~]{6,20}$",
                message = "{validation.password.message}")
        String password,

        @Pattern(regexp = "^(?=^.{0,50}$)([a-zA-Z0-9-_\\.]*[a-zA-Z0-9]@[a-z]+\\.[a-z]{2,6})?$",
                message = "{validation.email.message}")
        String email
) {}
