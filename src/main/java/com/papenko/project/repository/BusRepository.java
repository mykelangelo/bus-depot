package com.papenko.project.repository;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
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
            throw new RuntimeException("SQL fails to find all buses. Current list of buses: " + buses, e);
        }

        return buses;
    }

    public void updateBusSetRoute(Bus bus, Route route) {
        var sql = "UPDATE bus SET route_name = (?) " +
                "WHERE bus_serial = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, route.getName());
            preparedStatement.setString(2, bus.getSerialNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("SQL fails to update bus " + bus + " with route " + route, e);
        }

        Driver driver = driverRepository.findDriverByBus(bus);
        if (driver != null) {
            driverRepository.updateDriverSetAwareness(driver, false);
        }
    }

    public Bus findBusBySerialNumber(String busSerial) {
        var sql = "SELECT route_name FROM bus " +
                "WHERE bus_serial = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, busSerial);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String routeName = resultSet.getString("route_name");
                    Route route = routeRepository.findRouteByName(routeName);
                    return new Bus(busSerial, route);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL fails to find bus by serial " + busSerial, e);
        }
        return null;
    }
}
