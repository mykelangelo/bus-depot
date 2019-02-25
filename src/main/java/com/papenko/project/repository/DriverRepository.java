package com.papenko.project.repository;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {
    private final DataSource dataSource;
    private final BusRepository busRepository;

    public DriverRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        busRepository = new BusRepository(dataSource);
    }

    public void updateDriverSetBus(Driver driver, Bus bus) {
        var sql = "UPDATE bus_driver SET bus_serial = (?) " +
                "WHERE user_email = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bus.getSerialNumber());
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
                    Bus bus = busRepository.findBusBySerialNumber(busSerial);
                    return new Driver(driverEmail, bus);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Driver> findAllDrivers() {
        var sql = "SELECT user_email, bus_serial FROM bus_driver;";
        List<Driver> drivers = new ArrayList<>();

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String userEmail = resultSet.getString("user_email");
                String busSerial = resultSet.getString("bus_serial");
                Bus bus = busRepository.findBusBySerialNumber(busSerial);
                drivers.add(new Driver(userEmail, bus));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return drivers;
    }
}
