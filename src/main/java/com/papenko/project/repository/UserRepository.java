package com.papenko.project.repository;

import com.papenko.project.entity.User;
import com.papenko.project.entity.UserType;

import javax.sql.DataSource;
import java.sql.SQLException;

public class UserRepository {
    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findUserByEmail(String email) {
        var sql = "SELECT user_type, password_hash FROM depot_user WHERE email = (?);";

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UserType userType = UserType.valueOf(resultSet.getString("user_type"));
                    String passwordHash = resultSet.getString("password_hash");
                    return new User(email, userType, passwordHash);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
