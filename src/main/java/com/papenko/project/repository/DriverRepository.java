package com.papenko.project.repository;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverRepository.class);
    private final DataSource dataSource;
    private final BusRepository busRepository;

    public DriverRepository(DataSource dataSource, BusRepository busRepository) {
        this.dataSource = dataSource;
        this.busRepository = busRepository;
    }

    public void updateDriverSetBus(Driver driver, Bus bus) {
        var sql = "UPDATE bus_driver " +
                "SET bus_serial = (?), aware_of_assignment = FALSE " +
                "WHERE user_email = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bus.getSerialNumber());
            preparedStatement.setString(2, driver.getUserEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("SQL fails to update driver " + driver + " with bus " + bus, e);
            throw new RuntimeException(e);
        }
    }

    public Driver findDriverByEmail(String driverEmail) {
        var sql = "SELECT bus_serial, aware_of_assignment FROM bus_driver " +
                "WHERE user_email = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, driverEmail);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String busSerial = resultSet.getString("bus_serial");
                    boolean awareOfAssignment = resultSet.getBoolean("aware_of_assignment");
                    Bus bus = busRepository.findBusBySerialNumber(busSerial);
                    return new Driver(driverEmail, bus, awareOfAssignment);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL fails to find driver by email " + driverEmail, e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Driver> findAllDrivers() {
        var sql = "SELECT user_email, bus_serial, aware_of_assignment FROM bus_driver;";
        List<Driver> drivers = new ArrayList<>();

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String userEmail = resultSet.getString("user_email");
                String busSerial = resultSet.getString("bus_serial");
                boolean awareOfAssignment = resultSet.getBoolean("aware_of_assignment");
                Bus bus = busRepository.findBusBySerialNumber(busSerial);
                drivers.add(new Driver(userEmail, bus, awareOfAssignment));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL fails to find all drivers. Current list of drivers: " + drivers, e);
            throw new RuntimeException(e);
        }

        return drivers;
    }

    public void updateDriverSetAwareness(Driver driver, boolean isAwareOfAssignment) {
        var sql = "UPDATE bus_driver " +
                "SET aware_of_assignment = (?) " +
                "WHERE user_email = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, isAwareOfAssignment);
            preparedStatement.setString(2, driver.getUserEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("SQL fails to update driver " + driver + " with awareness " + isAwareOfAssignment, e);
            throw new RuntimeException(e);
        }
    }

    public Driver findDriverByBus(Bus bus) {
        var sql = "SELECT user_email, aware_of_assignment FROM bus_driver " +
                "WHERE bus_serial = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bus.getSerialNumber());
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String userEmail = resultSet.getString("user_email");
                    boolean awareOfAssignment = resultSet.getBoolean("aware_of_assignment");
                    return new Driver(userEmail, bus, awareOfAssignment);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL fails to find driver by bus " + bus, e);
            throw new RuntimeException(e);
        }
        return null;
    }
}
