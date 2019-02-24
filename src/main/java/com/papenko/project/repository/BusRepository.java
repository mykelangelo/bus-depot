package com.papenko.project.repository;

import com.papenko.project.entity.Bus;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusRepository {
    private final DataSource dataSource;

    public BusRepository(DataSource dataSource) {
        this.dataSource = dataSource;
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
                buses.add(new Bus(busSerial, routeName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return buses;
    }

    public void updateBus(Bus bus) {
        var sql = "UPDATE bus SET route_name = (?) " +
                "WHERE bus_serial = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bus.getRouteName());
            preparedStatement.setString(2, bus.getSerialNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                    return new Bus(busSerial, routeName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
