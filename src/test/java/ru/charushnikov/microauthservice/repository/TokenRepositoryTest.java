package ru.charushnikov.microauthservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.PassportData;
import ru.charushnikov.microauthservice.model.entity.Token;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getPassport;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TokenRepositoryTest {

    private static final String JWS = "eyJhbGciOiJIUzUxMiJ9.eyJjbGllbnRJZCI6ImM5NWU4NmViLWM3ZmYtNGY3OS05ZjY1LTVjYjYzMjEzOTNiMCIsInN1YiI6Ik9FR0ZFRUhMWkUyRiIsImlhdCI6MTY2ODcwNTQ4MywiZXhwIjoxNjY4NzA5MDgzfQ.--PwESRU9iU_smpIDGjWOSoMqtJD3AtaNbn6qQ3MYHETNPg8wlLKIAIfGoNHwTHamHfDu0B1E9oE_O_Qj6Rtbw";

    private static final String JWS_UPDATED = "XXXhbGciOiJIUzUxMiJ9.eyJjbGllbnRJZCI6ImM5NWU4NmViLWM3ZmYtNGY3OS05ZjY1LTVjYjYzMjEzOTNiMCIsInN1YiI6Ik9FR0ZFRUhMWkUyRiIsImlhdCI6MTY2ODcwNTQ4MywiZXhwIjoxNjY4NzA5MDgzfQ.--PwESRU9iU_smpIDGjWOSoMqtJD3AtaNbn6qQ3MYHETNPg8wlLKIAIfGoNHwTHamHfDu0B1E9oE_O_Qj6Rtbw";

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PassportDataRepository passportDataRepository;

    @Value("${micro.app.jwtExpirationMs}")
    private static int jwtExpirationMs;

    private static Token token;

    private static Token tokenDB;

    @BeforeEach
    void setup() {
        PassportData passportData = passportDataRepository.save(getPassport());

        Client client = clientRepository.save(Client.builder()
                .id(UUID.randomUUID())
                .firstName("Kolya")
                .lastName("Nikolaev")
                .mobilePhone("+79298282996")
                .passportData(passportData)
                .build());

        Date date = new Date(new Date().getTime() + jwtExpirationMs);
        token = Token.builder()
                .client(client)
                .token(JWS)
                .expiryDate(date.toInstant())
                .build();

        tokenDB = tokenRepository.save(token);
    }

    @Test
    void returnTrueWhenFindByIdToken() {
        assertTrue(tokenRepository.findById(tokenDB.getId()).isPresent());
    }

    @Test
    void returnTrueWhenSaveToken() {
        Token saveToken = tokenRepository.save(token);
        assertTrue(tokenRepository.findById(saveToken.getId()).isPresent());
    }

    @Test
    void returnEmptyWhenDeleteToken() {
        tokenRepository.delete(tokenDB);
        assertTrue(tokenRepository.findById(tokenDB.getId()).isEmpty());
    }

    @Test
    void updateToken() {
        Token newToken = Token.builder()
                .id(tokenDB.getId())
                .token(JWS_UPDATED)
                .build();

        assertEquals(JWS, tokenRepository.findById(tokenDB.getId()).get().getToken());
        Token updateToken = tokenRepository.save(newToken);
        assertEquals(JWS_UPDATED, tokenRepository.findById(updateToken.getId()).get().getToken());
        assertEquals(null, tokenRepository.findById(tokenDB.getId()).get().getClient());

    }
}
