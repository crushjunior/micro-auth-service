package ru.charushnikov.microauthservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;
import ru.charushnikov.microauthservice.integration.config.IntegrationTest;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getRegisterRequestDto;

@IntegrationTest
@Sql(value = {"classpath:sql/1_clear_schema.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RegistrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String BASE_URL = "/auth-service/registration";

    @Test
    void registerNonClientTest_shouldBeSuccessful() {
        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/user-profile/new")
                .build()
                .toUri();

        RegisterRequestDto registerRequestDto = getRegisterRequestDto();

        HttpEntity<RegisterRequestDto> requestEntity = new HttpEntity<>(registerRequestDto);

        ResponseEntity<Object> result = testRestTemplate
                .exchange(targetUrl, HttpMethod.POST, requestEntity, Object.class);


        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
