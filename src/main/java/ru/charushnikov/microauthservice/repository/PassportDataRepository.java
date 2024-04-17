package ru.charushnikov.microauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.charushnikov.microauthservice.model.entity.PassportData;

@Repository
public interface PassportDataRepository extends JpaRepository<PassportData, String> {
}
