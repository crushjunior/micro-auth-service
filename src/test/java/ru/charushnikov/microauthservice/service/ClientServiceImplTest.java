package ru.charushnikov.microauthservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.charushnikov.microauthservice.exception.DuplicatePassportException;
import ru.charushnikov.microauthservice.exception.DuplicatedPhoneException;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.repository.ClientRepository;
import ru.charushnikov.microauthservice.service.impl.ClientServiceImpl;
import ru.charushnikov.microauthservice.service.impl.PassportDataServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getClient;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getRegisterRequestDto;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PassportDataServiceImpl passportDataService;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    private RegisterRequestDto requestDto;

    @BeforeEach
    public void setup() {
        client = getClient();
        requestDto = getRegisterRequestDto();
    }

    @Test
    public void whenRegisterClient_isSuccessful() {
        when(clientRepository.findByMobilePhone(client.getMobilePhone())).thenReturn(Optional.empty());
        when(clientRepository.save(any())).thenReturn(client);
        Client savedClient = clientService.registerClient(requestDto);
        verify(passportDataService, atLeastOnce()).save(client.getPassportData());
        assertEquals(savedClient, client);
    }

    @Test
    public void whenRegisterClientFailed_thenExceptionThrows() {
        when(clientRepository.findByMobilePhone(client.getMobilePhone())).thenReturn(Optional.of(client));
        assertThrows(DuplicatedPhoneException.class, () -> clientService.registerClient(requestDto));
    }
}
