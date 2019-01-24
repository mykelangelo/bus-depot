package com.papenko.project;

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

    public String findUserPasswordHashByEmail(String email) {
        var sql = "SELECT password_hash FROM depot_user WHERE email = (?)";
        String result = null;
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getString("password_hash");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
