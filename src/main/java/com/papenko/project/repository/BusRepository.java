package com.papenko.project.repository;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
import com.papenko.project.exception.bus.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusRepository.class);
    private final DataSource dataSource;
    private final RouteRepository routeRepository;
    private final DriverRepository driverRepository;

    public BusRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        routeRepository = new RouteRepository(dataSource);
        driverRepository = new DriverRepository(dataSource, this);
    }

    public List<Bus> findAllBuses() {
        LOGGER.debug("about to find all buses");
        var sql = "SELECT bus_serial, route_name FROM bus;";
        List<Bus> buses = new ArrayList<>();

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String busSerial = resultSet.getString("bus_serial");
                String routeName = resultSet.getString("route_name");
                Route route = routeRepository.findRouteByName(routeName);
                buses.add(new Bus(busSerial, route));
            }
        } catch (SQLException e) {
            throw new BusesSearchException(buses, e);
        }

        LOGGER.debug("found all {} buses", buses.size());
        return buses;
    }

    public void updateBusSetRoute(Bus bus, Route route) {
        LOGGER.debug("about to update bus with a new route");
        var sql = "UPDATE bus SET route_name = (?) " +
                "WHERE bus_serial = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, route.getName());
            preparedStatement.setString(2, bus.getSerialNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new BusRouteChangeException(bus, route, e);
        }
        LOGGER.debug("updated bus with a new route");

        Driver driver = driverRepository.findDriverByBus(bus);
        if (driver != null) {
            driverRepository.updateDriverSetAwareness(driver, false);
            LOGGER.debug("also set awareness of driver in this bus to false");
        }
    }

    public Bus findBusBySerialNumber(String busSerial) {
        LOGGER.debug("about to find a bus by serial number");
        var sql = "SELECT route_name FROM bus " +
                "WHERE bus_serial = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, busSerial);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String routeName = resultSet.getString("route_name");
                    Route route = routeRepository.findRouteByName(routeName);
                    LOGGER.debug("found a bus by serial number");
                    return new Bus(busSerial, route);
                }
            }
        } catch (SQLException e) {
            throw new BusSearchException(busSerial, e);
        }
        LOGGER.debug("bus by serial number not found");
        return null;
    }

    public List<Bus> findBusesByRoute(Route route) {
        LOGGER.debug("about to find buses by route");
        var sql = "SELECT bus_serial FROM bus WHERE route_name = (?);";
        List<Bus> buses = new ArrayList<>();

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, route.getName());
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String busSerial = resultSet.getString("bus_serial");
                    buses.add(new Bus(busSerial, route));
                }
            }
        } catch (SQLException e) {
            throw new BusesSearchException(buses, route, e);
        }
        LOGGER.debug("found {} buses by route", buses.size());
        return buses;
    }

    public void createBus(String busSerial) {
        LOGGER.debug("about to create a bus");
        var sql = "INSERT INTO bus (bus_serial, route_name) VALUE ((?), NULL);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, busSerial);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new BusCreationException(busSerial, e);
        }
        LOGGER.debug("created a bus");
    }

    public void deleteBus(Bus bus) {
        LOGGER.debug("about to delete a bus");
        var sql = "DELETE FROM bus WHERE bus_serial = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bus.getSerialNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new BusDeletionException(bus, e);
        }
        LOGGER.debug("deleted a bus");
    }
}
