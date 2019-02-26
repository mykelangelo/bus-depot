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
        LOGGER.debug("checking credentials");
        User user = userRepository.findUserByEmail(email);

        if (user != null) {
            return BCrypt.checkpw(passwordInPlainText, user.getPasswordHash());
        } else {
            LOGGER.debug("user is null");
            return false;
        }
    }


    public UserType getUserType(String email) {
        return userRepository.findUserByEmail(email).getUserType();
    }
}