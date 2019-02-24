package com.papenko.project.service;

import com.papenko.project.entity.User;
import com.papenko.project.entity.UserType;
import com.papenko.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
        User user = new User("correct_email@company.yes", UserType.BUS_DRIVER, "$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy");
        given(userRepository.findUserByEmail("correct_email@company.yes")).willReturn(user);
        // WHEN
        boolean credentialsAreCorrect = loginService.checkCredentials("correct_email@company.yes", "correctPasswordWhyNotItsAGreatOne");
        // THEN
        assertTrue(credentialsAreCorrect);
    }

    @Test
    void checkCredentials_shouldReturnFalse_whenPasswordDoesNotMatchPasswordHashForExistingUserWithEmailGiven() {
        // GIVEN
        User user = new User("driver@gmail.com", UserType.BUS_DRIVER, "$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG");
        given(userRepository.findUserByEmail("correct_email@yes")).willReturn(user);
        // WHEN
        boolean credentialsAreCorrect = loginService.checkCredentials("correct_email@yes", "wrongPassword");
        // THEN
        assertFalse(credentialsAreCorrect);
    }

    @Test
    void checkCredentials_shouldReturnFalse_whenNoUserFoundWithEmailGiven() {
        // GIVEN
        given(userRepository.findUserByEmail(anyString())).willReturn(null);
        // WHEN
        boolean credentialsAreCorrect = loginService.checkCredentials("noUserWithSuch@Email.exists", "anyPassword");
        // THEN
        assertFalse(credentialsAreCorrect);
    }

    @Test
    void getUserType_shouldReturnDriverUserType_whenDriversEmailIsPassed() {
        // GIVEN
        User driverUser = new User("driver@gmail.com", UserType.BUS_DRIVER, "$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG");
        given(userRepository.findUserByEmail("driver@gmail.com")).willReturn(driverUser);
        // WHEN
        UserType userType = loginService.getUserType("driver@gmail.com");
        // THEN
        assertEquals(UserType.BUS_DRIVER, userType);
    }

    @Test
    void getUserType_shouldReturnAdminsUserType_whenAdminsEmailIsPassed() {
        // GIVEN
        User adminUser = new User("admin@gmail.com", UserType.DEPOT_ADMIN, "$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG");
        given(userRepository.findUserByEmail("adminPage@gmail.com")).willReturn(adminUser);
        // WHEN
        UserType userType = loginService.getUserType("adminPage@gmail.com");
        // THEN
        assertEquals(UserType.DEPOT_ADMIN, userType);
    }
}