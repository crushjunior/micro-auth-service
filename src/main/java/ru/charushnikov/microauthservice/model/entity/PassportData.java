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
    @Column(name = "passport_number")
    private String identificationPassportNumber;

    @Column(name = "issuance_date", nullable = false)
    private LocalDate issuanceDate;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
}

