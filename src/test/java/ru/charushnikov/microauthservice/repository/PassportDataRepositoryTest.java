package ru.charushnikov.microauthservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.charushnikov.microauthservice.model.entity.PassportData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getPassport;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PassportDataRepositoryTest {

    @Autowired
    private PassportDataRepository passportDataRepository;

    private static PassportData passportData;

    private static PassportData passportData2;

    @BeforeEach
    void setup() {
        passportData2 = passportDataRepository.save(PassportData.builder()
                .identificationPassportNumber("9987654321")
                .birthDate(LocalDate.of(2000, 11, 11))
                .build());

        passportData = getPassport();
    }

    @Test
    void returnTrueWhenSavePassport(){
        passportData = passportDataRepository.save(passportData);
        assertTrue(passportDataRepository.findById(passportData.getIdentificationPassportNumber()).isPresent());
    }

    @Test
    void returnTrueWhenFindById() {
assertTrue(passportDataRepository.findById(passportData2.getIdentificationPassportNumber()).isPresent());
    }

    @Test
    void returnNullWhenDeleteClient(){
        passportDataRepository.delete(passportData2);
        assertTrue(passportDataRepository.findById(passportData2.getIdentificationPassportNumber()).isEmpty());
    }

    @Test
    void updatePassport() {
        PassportData newPassport = PassportData.builder()
                .identificationPassportNumber("9987654321")
                .birthDate(LocalDate.of(2020, 12, 12))
                .build();

        PassportData updatePassport = passportDataRepository.findById(passportData2.getIdentificationPassportNumber()).get();
        assertNotEquals(updatePassport.getBirthDate(), newPassport.getBirthDate());
        updatePassport = passportDataRepository.save(newPassport);
        assertEquals(updatePassport.getBirthDate(), newPassport.getBirthDate());
    }
}
