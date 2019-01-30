package com.papenko.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

    @Test
    void getUserType_shouldReturnDriverUserType_whenDriversEmailIsPassed() {
        // GIVEN
        given(userRepository.findUserTypeByEmail("driver@gmail.com")).willReturn("BUS_DRIVER");
        // WHEN
        UserType userType = loginService.getUserType("driver@gmail.com");
        // THEN
        assertEquals(UserType.BUS_DRIVER, userType);
    }

    @Test
    void getUserType_shouldReturnAdminsUserType_whenAdminsEmailIsPassed() {
        // GIVEN
        given(userRepository.findUserTypeByEmail("admin@gmail.com")).willReturn("DEPOT_ADMIN");
        // WHEN
        UserType userType = loginService.getUserType("admin@gmail.com");
        // THEN
        assertEquals(UserType.DEPOT_ADMIN, userType);
    }
}