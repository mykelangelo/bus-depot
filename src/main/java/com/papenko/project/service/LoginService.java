package com.papenko.project.service;

import com.papenko.project.entity.User;
import com.papenko.project.entity.UserType;
import com.papenko.project.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkCredentials(String email, String passwordInPlainText) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            return BCrypt.checkpw(passwordInPlainText, user.getPasswordHash());
        } else {
            return false;
        }
    }


    public UserType getUserType(String email) {
        return userRepository.findUserByEmail(email).getUserType();
    }
}