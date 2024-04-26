package ru.charushnikov.microauthservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.charushnikov.microauthservice.exception.DuplicatePassportException;
import ru.charushnikov.microauthservice.model.entity.PassportData;
import ru.charushnikov.microauthservice.repository.PassportDataRepository;
import ru.charushnikov.microauthservice.service.impl.PassportDataServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getPassport;

@ExtendWith(MockitoExtension.class)
public class PassportDataServiceImplTest {

    @Mock
    private PassportDataRepository passportDataRepository;

    @InjectMocks
    private PassportDataServiceImpl passportDataService;

    private PassportData passportData;

    @BeforeEach
    public void setup() {
        passportData = getPassport();
    }

    @Test
    public void whenSavePassport_isSuccessful() {
        doReturn(passportData).when(passportDataRepository).save(passportData);
        PassportData savedPassport = passportDataService.save(passportData);
        assertEquals(savedPassport.getIdentificationPassportNumber(), passportData.getIdentificationPassportNumber());
        assertEquals(savedPassport.getBirthDate(), passportData.getBirthDate());
        assertEquals(savedPassport.getIssuanceDate(), passportData.getIssuanceDate());
        verify(passportDataRepository, atLeastOnce()).save(passportData);
    }

    @Test
    public void whenSavePassportFailed_thenExceptionThrows() {
        when(passportDataRepository.existsById(passportData.getIdentificationPassportNumber())).thenReturn(true);
        assertThrows(DuplicatePassportException.class, () -> passportDataService.save(passportData));
    }
}
