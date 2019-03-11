package com.papenko.project.repository;

import com.papenko.project.entity.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteRepository.class);
    private final DataSource dataSource;

    public RouteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Route> findAllRoutes() {
        var sql = "SELECT route_name FROM route;";
        List<Route> routes = new ArrayList<>();

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String routeName = resultSet.getString("route_name");
                routes.add(new Route(routeName));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL fails to find all routes. Current list of routes: " + routes, e);
            throw new RuntimeException(e);
        }

        return routes;
    }

    public Route findRouteByName(String routeName) {
        var sql = "SELECT route_name FROM route " +
                "WHERE route_name = (?);";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, routeName);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Route(routeName);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL fails to find route by name " + routeName, e);
            throw new RuntimeException(e);
        }
        return null;
    }
}
