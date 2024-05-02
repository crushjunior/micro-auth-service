package ru.charushnikov.microauthservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.charushnikov.microauthservice.model.entity.UserProfile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.charushnikov.microauthservice.generator.EntityGeneratorTest.getUserProfile;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    private static UserProfile userProfile;

    @BeforeEach
    void setup() {
        userProfile = userProfileRepository.save(getUserProfile());
    }

    @Test
    void returnTrueWhenFindProfileById() {
        assertTrue(userProfileRepository.findById(userProfile.getId()).isPresent());
    }

    @Test
    void returnTrueWhenSaveProfile() {
        UserProfile savedProfile = userProfileRepository.save(getUserProfile());
        assertTrue(userProfileRepository.findById(savedProfile.getId()).isPresent());
    }

    @Test
    void returnEmptyWhenDeleteProfile() {
        userProfileRepository.delete(userProfile);
        assertTrue(userProfileRepository.findById(userProfile.getId()).isEmpty());
    }

    @Test
    void updateProfile() {
        UserProfile newProfile = UserProfile.builder()
                .id(userProfile.getId())
                .email("box@mail.com")
                .build();

        assertEquals("blabla@gmail.com", userProfileRepository.findById(userProfile.getId()).get().getEmail());
        UserProfile updateProfile = userProfileRepository.save(newProfile);
        assertEquals("box@mail.com", userProfileRepository.findById(updateProfile.getId()).get().getEmail());
        assertEquals(null, userProfileRepository.findById(updateProfile.getId()).get().getPassword());
    }
}
