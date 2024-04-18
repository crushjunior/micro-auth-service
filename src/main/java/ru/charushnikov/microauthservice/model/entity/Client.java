package ru.charushnikov.microauthservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_phone")
    @Pattern(regexp = "\\+7\\d{10}"/*,
            message = "Mobile phone must start with +7 and contain 11 digits"*/)
    private String mobilePhone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_number")
    private PassportData passportData;
}

