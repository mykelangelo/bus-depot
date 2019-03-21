package com.papenko.project.repository;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Route;
import com.papenko.project.exception.bus.BusCreationException;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScripts;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.junit.jupiter.api.Assertions.*;

class BusRepositoryTest {
    private BusRepository busRepository;
    private DriverRepository driverRepository;
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

        busRepository = new BusRepository(
                getDataSource()
        );
        driverRepository = new DriverRepository(
                getDataSource(),
                new BusRepository(
                        getDataSource()
                )
        );
    }

    private HikariDataSource getDataSource() {
        return new HikariDataSource(
                new HikariConfig("src/test/resources/db/connection-pool.properties")
        );
    }

    @AfterEach
    void shutDown() {
        embeddedMysql.stop();
    }


    @Test
    void findAllBuses_shouldReturnSerialsOfEveryBus() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUES " +
                        "('IA9669SA', '7L')," +
                        "('FI6669CT', '7L');");
        // WHEN
        List<Bus> busesSerials = busRepository.findAllBuses();
        // THEN
        assertEquals(List.of(new Bus("FI6669CT", new Route("7L")), new Bus("IA9669SA", new Route("7L"))), busesSerials);
    }

    @Test
    void updateBusSetRoute_shouldUpdateRouteOfBus_andSetAwarenessOfDriverInThisBusToFalse_whenSuchDriverExists() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUES ('7L'), ('96'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L');" +
                        "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUES ('aware@driver.cn', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial, aware_of_assignment)" +
                        " VALUE ('aware@driver.cn', 'IA9669SA', TRUE);");
        // WHEN
        busRepository.updateBusSetRoute(new Bus("IA9669SA", new Route("7L")), new Route("96"));
        // THEN
        assertEquals(new Bus("IA9669SA", new Route("96")),
                busRepository.findBusBySerialNumber("IA9669SA"));
        assertFalse(driverRepository.findDriverByEmail("aware@driver.cn").isAwareOfAssignment());
    }

    @Test
    void updateBusSetRoute_shouldUpdateRouteOfBus_whenNoSuchDriverExists() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUES ('7L'), ('96'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L');");
        // WHEN
        busRepository.updateBusSetRoute(new Bus("IA9669SA", new Route("7L")), new Route("96"));
        // THEN
        assertEquals(new Bus("IA9669SA", new Route("96")),
                busRepository.findBusBySerialNumber("IA9669SA"));
    }

    @Test
    void findBusBySerialNumber_shouldReturnBus_whenBusWithSuchSerialExists() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L');");
        // WHEN
        Bus bus = busRepository.findBusBySerialNumber("IA9669SA");
        // THEN
        assertEquals(new Bus("IA9669SA", new Route("7L")), bus);
    }

    @Test
    void findBusBySerialNumber_shouldReturnNull_whenNoBusExistsWithSuchSerial() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L');");
        // WHEN
        Bus bus = busRepository.findBusBySerialNumber("LL009UU");
        // THEN
        assertNull(bus);
    }

    @Test
    void findBusesByRoute_shouldReturnListWithEveryBusOnRoute() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUES ('7L'), ('11'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUES ('IA9669SA', '7L'), ('USA 8', '11'), ('USS 55', '11');");
        // WHEN
        List<Bus> busesByRoute = busRepository.findBusesByRoute(new Route("11"));
        // THEN
        assertEquals(List.of(new Bus("USA 8", new Route("11")), new Bus("USS 55", new Route("11"))),
                busesByRoute);
    }

    @Test
    void createBus_shouldInsertNewBusWithSerialGivenIntoTableBus_whenNoSuchBusExistsInTableBus() {
        // GIVEN
        // WHEN
        busRepository.createBus("OW 144");
        // THEN
        assertEquals(new Bus("OW 144", null), busRepository.findBusBySerialNumber("OW 144"));
    }

    @Test
    void createRoute_shouldThrowRouteCreationException_whenRouteWithNameGivenAlreadyExistsInTableRoute() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO bus (bus_serial, route_name) VALUE ('HMS 45', NULL);");
        // WHEN
        Executable routeCreation = () -> busRepository.createBus("HMS 45");
        // THEN
        assertThrows(BusCreationException.class, routeCreation);
    }
}