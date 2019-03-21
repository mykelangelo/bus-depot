package com.papenko.project.repository;

import com.papenko.project.entity.User;
import com.papenko.project.entity.UserType;
import com.papenko.project.exception.user.UserDeletionException;
import com.papenko.project.exception.user.UserSearchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findUserByEmail(String email) {
        LOGGER.debug("about to find a user by email");
        var sql = "SELECT user_type, password_hash FROM depot_user WHERE email = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UserType userType = UserType.valueOf(resultSet.getString("user_type"));
                    String passwordHash = resultSet.getString("password_hash");
                    LOGGER.debug("found a user by email");
                    return new User(email, userType, passwordHash);
                }
            }
        } catch (SQLException e) {
            throw new UserSearchException(email, e);
        }
        LOGGER.debug("user by email not found");
        return null;
    }

    public void deleteUser(User user) {
        LOGGER.debug("about to delete a user");
        var sql = "DELETE FROM depot_user WHERE email = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new UserDeletionException(user, e);
        }
        LOGGER.debug("deleted a user");
    }
}
