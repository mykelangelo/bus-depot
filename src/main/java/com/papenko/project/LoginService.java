package com.papenko.project;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

class LoginService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private final UserRepository userRepository;

    LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    boolean checkCredentials(String email, String passwordInPlainText) {
        String userPasswordHash = userRepository.findUserPasswordHashByEmail(email);
        if (userPasswordHash != null) {
            return BCrypt.checkpw(passwordInPlainText, userPasswordHash);
        } else {
            return false;
        }
    }


    String getLandingAdminOrDriverPageDependingOnTypeOfUser(String email) {
        String userType = userRepository.findUserTypeByEmail(email);
        return StringUtils.join('/', userType,".jsp");
    }
}