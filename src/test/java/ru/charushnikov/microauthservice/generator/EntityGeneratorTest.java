package ru.charushnikov.microauthservice.generator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.dto.response.RegisterResponseDto;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.PassportData;
import ru.charushnikov.microauthservice.model.entity.UserProfile;

import java.time.LocalDate;
import java.util.UUID;

public class EntityGeneratorTest {
    private static final UUID USER_PROFILE_ID = UUID.randomUUID();
    private static final String EMAIL = "blabla@gmail.com";
    private static final String RAW_PASSWORD = "P@ssword1";
    private static final String PASSPORT_NUMBER = "9216023953";
    private static final String MOBILE_PHONE = "89275293028";
    private static final UUID CLIENT_ID = UUID.randomUUID();
    private static final String FIRST_NAME = "Eduard";
    private static final String LAST_NAME = "Petrenko";
    private static final LocalDate ISSUANCE_DATE = LocalDate.of(2020, 12, 30);
    private static final LocalDate BIRTH_DATE = LocalDate.of(2000, 03, 14);
    private static BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public static PassportData getPassport() {
        return PassportData.builder()
                .identificationPassportNumber(PASSPORT_NUMBER)
                .issuanceDate(ISSUANCE_DATE)
                .birthDate(BIRTH_DATE)
                .build();
    }

    public static Client getClient() {
        PassportData passportData = getPassport();
        return Client.builder()
                .id(CLIENT_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .passportData(passportData)
                .mobilePhone(MOBILE_PHONE)
                .build();
    }

    public static UserProfile getUserProfile() {
        Client client = getClient();
        return UserProfile.builder()
                .id(USER_PROFILE_ID)
                .password(encoder.encode(RAW_PASSWORD))
                .appRegistrationDate(LocalDate.now())
                .email(EMAIL)
                .client(client)
                .build();
    }

    public static RegisterRequestDto getRegisterRequestDto() {
        return RegisterRequestDto.builder()
                .identificationPassportNumber(PASSPORT_NUMBER)
                .birthDate(BIRTH_DATE)
                .issuanceDate(ISSUANCE_DATE)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .mobilePhone(MOBILE_PHONE)
                .password(RAW_PASSWORD)
                .email(EMAIL)
                .build();
    }

    public static RegisterResponseDto getRegisterResponseDto() {
        return RegisterResponseDto.builder()
                .id(USER_PROFILE_ID)
                .mobilePhone(MOBILE_PHONE)
                .passwordEncoded(encoder.encode(RAW_PASSWORD))
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .identificationPassportNumber(PASSPORT_NUMBER)
                .email(EMAIL)
                .appRegistrationDate(LocalDate.now())
                .build();
    }
}
