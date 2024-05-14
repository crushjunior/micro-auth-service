package ru.charushnikov.microauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.charushnikov.microauthservice.model.entity.UserProfile;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    Boolean existsByEmail(String email);

//    @Query(value = "SELECT u.* FROM user_profile u " +
//            "JOIN client c  ON c.id = u.client_id " +
//            "WHERE c.mobile_phone = :mobilePhone", nativeQuery = true)
    @Query("SELECT u FROM UserProfile u " +
            "JOIN FETCH u.client " +
            "WHERE u.client.mobilePhone = :mobilePhone")
    Optional<UserProfile> findByMobilePhone(String mobilePhone);
}
