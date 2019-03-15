package com.papenko.project.service;

import com.papenko.project.entity.User;
import com.papenko.project.entity.UserType;
import com.papenko.project.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkCredentials(String email, String passwordInPlainText) {
        LOGGER.debug("about to check credentials");
        User user = userRepository.findUserByEmail(email);

        if (user != null) {
            boolean passwordCheckResult = BCrypt.checkpw(passwordInPlainText, user.getPasswordHash());
            LOGGER.debug("finished checking credentials");
            return passwordCheckResult;
        } else {
            LOGGER.debug("user is null; finished checking credentials");
            return false;
        }
    }

    public UserType getUserType(String email) {
        LOGGER.debug("about to get user by email");
        UserType userType = userRepository.findUserByEmail(email).getUserType();
        LOGGER.debug("finished getting user by email");
        return userType;
    }
}