package com.papenko.project.repository;

import com.papenko.project.entity.Driver;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DriverRepository {
    private final DataSource dataSource;

    public DriverRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void updateDriver(Driver driver) {
        var sql = "UPDATE bus_driver SET bus_serial = (?) " +
                "WHERE user_email = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, driver.getBusSerial());
            preparedStatement.setString(2, driver.getUserEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Driver findDriverByEmail(String driverEmail) {
        var sql = "SELECT bus_serial FROM bus_driver " +
                "JOIN depot_user ON depot_user.email = bus_driver.user_email " +
                "WHERE bus_driver.user_email = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, driverEmail);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String busSerial = resultSet.getString("bus_serial");
                    return new Driver(driverEmail, busSerial);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
