package ru.charushnikov.microauthservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "passport_data")
public class PassportData {
    @Id
    private String identificationPassportNumber;

    @Column(name = "issuance_date")
    private LocalDate issuanceDate;

    @Column(name = "birth_date")
    private LocalDate birthDate;
}

