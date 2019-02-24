package com.papenko.project.repository;

import com.papenko.project.entity.Route;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteRepository {
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
            throw new RuntimeException(e);
        }

        return routes;
    }
}
