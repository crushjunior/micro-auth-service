package ru.charushnikov.microauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.charushnikov.microauthservice.model.entity.Token;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByClientId(UUID clientId);

    @Modifying
    @Query("update Token t set t.token = :newToken where t.id = :tokenId")
    void updateToken(String newToken, Long tokenId);

}
