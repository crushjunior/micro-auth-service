package ru.charushnikov.microauthservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.charushnikov.microauthservice.exception.DuplicatePassportException;
import ru.charushnikov.microauthservice.model.entity.PassportData;
import ru.charushnikov.microauthservice.repository.PassportDataRepository;
import ru.charushnikov.microauthservice.service.PassportDataService;

@Service
@RequiredArgsConstructor
public class PassportDataServiceImpl implements PassportDataService {

    private final PassportDataRepository passportDataRepository;


    @Override
    public PassportData save(PassportData passportData) {
        String passportNumber = passportData.getIdentificationPassportNumber();
        if (passportDataRepository.existsById(passportNumber)) {
            throw new DuplicatePassportException("Passport is already in use");
        }

        return passportDataRepository.save(passportData);
    }
}
