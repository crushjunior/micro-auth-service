package ru.charushnikov.microauthservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.PassportData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getClient;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {

    @Autowired
    private  ClientRepository clientRepository;

    private static Client client;

    private static Client client2;

    @BeforeEach
    void setup() {
        client = clientRepository.save(getClient());

        client2 = Client.builder()
                .id(UUID.randomUUID())
                .firstName("Ivan")
                .lastName("Ivanov")
                .passportData(PassportData.builder()
                        .identificationPassportNumber("1111111111")
                        .build())
                .mobilePhone("89991234354")
                .build();
    }

    @Test
    void returnTrueIfClientExists() {
        assertTrue(clientRepository.findById(client.getId()).isPresent());
    }

    @Test
    void returnTrueWhenSaveClient() {
        client2 = clientRepository.save(client2);
        assertTrue(clientRepository.findById(client2.getId()).isPresent());
    }

    @Test
    void returnEmptyWhenDeleteClient() {
        clientRepository.delete(client);
        assertTrue(clientRepository.findById(client.getId()).isEmpty());
    }

    @Test
    void updateClient() {
        Client newClient = Client.builder()
                .firstName("Vladimir")
                .id(client.getId())
                .build();

        assertEquals(clientRepository.findById(client.getId()).get().getFirstName(), "Eduard");
        Client updateClient = clientRepository.save(newClient);
        assertEquals(clientRepository.findById(updateClient.getId()).get().getFirstName(), "Vladimir");
        assertEquals(clientRepository.findById(updateClient.getId()).get().getMobilePhone(), null);
    }
}
