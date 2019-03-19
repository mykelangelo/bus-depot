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
        LOGGER.debug("about to update driver with a new bus");
        var sql = "UPDATE bus_driver " +
                "SET bus_serial = (?), aware_of_assignment = FALSE " +
                "WHERE user_email = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bus.getSerialNumber());
            preparedStatement.setString(2, driver.getUserEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("SQL fails to update driver {} with bus {}", driver, bus, e);
        }
        LOGGER.debug("updated driver with a new bus");
    }

    public Driver findDriverByEmail(String driverEmail) {
        LOGGER.debug("about to find a driver by email");
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
                    LOGGER.debug("found a driver by email");
                    return new Driver(driverEmail, bus, awareOfAssignment);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL fails to find driver by email {}", driverEmail, e);
        }
        LOGGER.debug("driver by email not found");
        return null;
    }

    public List<Driver> findAllDrivers() {
        LOGGER.debug("about to find all drivers");
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
            LOGGER.error("SQL fails to find all drivers. Current list of drivers: {}", drivers, e);
        }

        LOGGER.debug("found all {} drivers", drivers.size());
        return drivers;
    }

    public void updateDriverSetAwareness(Driver driver, boolean isAwareOfAssignment) {
        LOGGER.debug("about to update driver with new awareness status");
        var sql = "UPDATE bus_driver " +
                "SET aware_of_assignment = (?) " +
                "WHERE user_email = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, isAwareOfAssignment);
            preparedStatement.setString(2, driver.getUserEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("SQL fails to update driver {} with awareness {}", driver, isAwareOfAssignment, e);
        }
        LOGGER.debug("updated driver with new awareness status");
    }

    public Driver findDriverByBus(Bus bus) {
        LOGGER.debug("about to find a driver by bus");
        var sql = "SELECT user_email, aware_of_assignment FROM bus_driver " +
                "WHERE bus_serial = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bus.getSerialNumber());
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String userEmail = resultSet.getString("user_email");
                    boolean awareOfAssignment = resultSet.getBoolean("aware_of_assignment");
                    LOGGER.debug("found a driver by bus");
                    return new Driver(userEmail, bus, awareOfAssignment);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL fails to find driver by bus {}", bus, e);
        }
        LOGGER.debug("driver by bus not found");
        return null;
    }
}
