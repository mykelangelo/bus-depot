package com.papenko.project;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    boolean checkCredentials(String email, String passwordInPlainText) {
        String userPasswordHash = userRepository.findUserPasswordHashByEmail(email); //тут null кидається
        if (userPasswordHash != null) {
            return BCrypt.checkpw(passwordInPlainText, userPasswordHash);
        } else {
            return false;
        }
    }
}