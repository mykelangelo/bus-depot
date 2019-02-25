package com.papenko.project.repository;

import com.papenko.project.entity.Route;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScripts;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RouteRepositoryTest {
    private RouteRepository routeRepository;
    private EmbeddedMysql embeddedMysql;

    @BeforeEach
    void setUp() {
        embeddedMysql = anEmbeddedMysql(
                aMysqldConfig(v5_7_latest)
                        .withUser("depot_user", "depot_password")
                        .withPort(4406)
                        .build()
        ).addSchema("depot_database", classPathScripts("db/schema/migration/*.sql")
        ).start();

        routeRepository = new RouteRepository(
                new HikariDataSource(
                        new HikariConfig("src/test/resources/db/connection-pool.properties")
                )
        );
    }

    @AfterEach
    void shutDown() {
        embeddedMysql.stop();
    }

    @Test
    void findAllRoutes_shouldReturnAllRoutes() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUES ('7L'), ('71');");
        // WHEN
        List<Route> routes = routeRepository.findAllRoutes();
        // THEN
        assertEquals(List.of(new Route("71"), new Route("7L")), routes);
    }

    @Test
    void findRouteByName_shouldReturnRoute_ifRouteWithSuchNameExists() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L');");
        // WHEN
        Route route = routeRepository.findRouteByName("7L");
        // THEN
        assertEquals(new Route("7L"), route);
    }

    @Test
    void findRouteByName_shouldReturnNull_ifNoRouteWithSuchNameExists() {
        // GIVEN
        // WHEN
        Route route = routeRepository.findRouteByName("H4");
        // THEN
        assertNull(route);
    }
}