package com.papenko.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    LoginService loginService;

    @Test
    void checkCredentials_shouldReturnTrue_whenPasswordMatchesPasswordHashForExistingUserWithEmailGiven() {
        // GIVEN
        given(userRepository.findUserPasswordHashByEmail("correct_email@company.yes"))
                .willReturn("$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy");
        // WHEN
        boolean credentialsAreCorrect = loginService.checkCredentials("correct_email@company.yes", "correctPasswordWhyNotItsAGreatOne");
        // THEN
        assertTrue(credentialsAreCorrect);
    }

    @Test
    void checkCredentials_shouldReturnFalse_whenPasswordDoesNotMatchPasswordHashForExistingUserWithEmailGiven() {
        // GIVEN
        given(userRepository.findUserPasswordHashByEmail("correct_email@yes"))
                .willReturn("$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG");
        // WHEN
        boolean credentialsAreCorrect = loginService.checkCredentials("correct_email@yes", "wrongPassword");
        // THEN
        assertFalse(credentialsAreCorrect);
    }

    @Test
    void checkCredentials_shouldReturnFalse_whenNoUserFoundWithEmailGiven() {
        // GIVEN
        given(userRepository.findUserPasswordHashByEmail(anyString())).willReturn(null);
        // WHEN
        boolean credentialsAreCorrect = loginService.checkCredentials("noUserWithSuch@Email.exists", "anyPassword");
        // THEN
        assertFalse(credentialsAreCorrect);
    }
}