package ru.charushnikov.microauthservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.charushnikov.microauthservice.exception.DuplicatedPhoneException;
import ru.charushnikov.microauthservice.model.dto.request.RegisterRequestDto;
import ru.charushnikov.microauthservice.model.entity.Client;
import ru.charushnikov.microauthservice.model.entity.PassportData;
import ru.charushnikov.microauthservice.repository.ClientRepository;
import ru.charushnikov.microauthservice.service.ClientService;
import ru.charushnikov.microauthservice.service.PassportDataService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final PassportDataService passportDataService;

    private final ClientRepository clientRepository;

    @Transactional
    public Client registerClient(RegisterRequestDto dto) {
        clientRepository.findByMobilePhone(dto.getMobilePhone())
                .ifPresent(client -> {
                    throw new DuplicatedPhoneException("Phone is already in use");
                });

        PassportData passportData = passportDataService.save(PassportData.builder()
                .birthDate(dto.getBirthDate())
                .identificationPassportNumber(dto.getIdentificationPassportNumber())
                .issuanceDate(dto.getIssuanceDate())
                .build());

        Client client = Client.builder()
                .mobilePhone(dto.getMobilePhone())
                .firstName(dto.getFirstName())
                .passportData(passportData)
                .lastName(dto.getLastName())
                .build();

        return clientRepository.save(client);
    }
}
