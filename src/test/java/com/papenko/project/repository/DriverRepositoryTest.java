package com.papenko.project.repository;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
import com.papenko.project.exception.driver.DriverCreationException;
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

class DriverRepositoryTest {
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
    void updateDriverSetBus_shouldUpdateBusOfDriver_andSetAwarenessToFalse() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUES ('IA9669SA', '7L'), ('FI6669CT', '7L'); " +
                        "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUE ('bus.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial, aware_of_assignment)" +
                        " VALUE ('bus.driver@yes', 'IA9669SA', TRUE);");
        // WHEN
        driverRepository.updateDriverSetBus(new Driver("bus.driver@yes", new Bus("IA9669SA", new Route("7L")), true),
                new Bus("FI6669CT", new Route("7L")));
        // THEN
        Driver driver = driverRepository.findDriverByEmail("bus.driver@yes");
        assertEquals("FI6669CT", driver.getBus().getSerialNumber());
        assertFalse(driver.isAwareOfAssignment());
    }

    @Test
    void findDriverByEmail_shouldReturnDriver_whenDriverWithSuchEmailExists() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L'); " +
                        "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUE ('bus.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial, aware_of_assignment)" +
                        " VALUE ('bus.driver@yes', 'IA9669SA', FALSE);");
        // WHEN
        Driver driver = driverRepository.findDriverByEmail("bus.driver@yes");
        // THEN
        assertEquals(new Driver("bus.driver@yes", new Bus("IA9669SA", new Route("7L")), false),
                driver);
    }

    @Test
    void findDriverByEmail_shouldReturnNull_whenNoDriverExistsWithSuchEmail() {
        // GIVEN
        // WHEN
        Driver driver = driverRepository.findDriverByEmail("random.driver@yes");
        // THEN
        assertNull(driver);
    }

    @Test
    void findAllDrivers_shouldReturnListWithAllDrivers() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUES ('IA9669SA', '7L'), ('GG777HH', '7L'); " +
                        "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUES ('bus.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG')," +
                        " ('hell.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial, aware_of_assignment)" +
                        " VALUES ('bus.driver@yes', 'IA9669SA', FALSE), ('hell.driver@yes', 'GG777HH', TRUE);");
        // WHEN
        List<Driver> drivers = driverRepository.findAllDrivers();
        // THEN
        assertEquals(List.of(
                new Driver("bus.driver@yes", new Bus("IA9669SA", new Route("7L")), false),
                new Driver("hell.driver@yes", new Bus("GG777HH", new Route("7L")), true)),
                drivers);
    }

    @Test
    void updateDriverSetAwareness_shouldSetAwarenessToTrue_whenTrueIsPassed() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L'); " +
                        "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUE ('the.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial, aware_of_assignment)" +
                        " VALUE ('the.driver@yes', 'IA9669SA', FALSE);");
        // WHEN
        driverRepository.updateDriverSetAwareness(driverRepository.findDriverByEmail("the.driver@yes"), true);
        // THEN
        assertTrue(driverRepository.findDriverByEmail("the.driver@yes").isAwareOfAssignment());
    }

    @Test
    void updateDriverSetAwareness_shouldSetAwarenessToFalse_whenFalseIsPassed() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L'); " +
                        "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUE ('the.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial, aware_of_assignment)" +
                        " VALUE ('the.driver@yes', 'IA9669SA', TRUE);");
        // WHEN
        driverRepository.updateDriverSetAwareness(driverRepository.findDriverByEmail("the.driver@yes"), false);
        // THEN
        assertFalse(driverRepository.findDriverByEmail("the.driver@yes").isAwareOfAssignment());
    }

    @Test
    void findDriverByBusSerial_shouldReturnDriverAssignedToGivenBus_whenSuchDriverExists() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L'); " +
                        "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUES ('bus.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial, aware_of_assignment)" +
                        " VALUE ('bus.driver@yes', 'IA9669SA', FALSE);");
        // WHEN
        Driver driver = driverRepository.findDriverByBus(new Bus("IA9669SA", new Route("7L")));
        // THEN
        assertEquals(driverRepository.findDriverByEmail("bus.driver@yes"),
                driver);
    }

    @Test
    void findDriverByBusSerial_shouldReturnNull_whenNoSuchDriverExists() {
        // GIVEN
        // WHEN
        Driver driver = driverRepository.findDriverByBus(new Bus("IA9669SA", new Route("7L")));
        // THEN
        assertNull(driver);
    }

    @Test
    void createDriver_shouldInsertNewDriverIntoTableBusDriver_whenNoSuchDriverExists() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUES ('driver@surprise.me', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');");
        // WHEN
        driverRepository.createDriver("driver@surprise.me");
        // THEN
        assertEquals(new Driver("driver@surprise.me", null, false),
                driverRepository.findDriverByEmail("driver@surprise.me"));
    }

    @Test
    void createDriver_shouldThrowUserCreationException_whenSuchUserAlreadyExists() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUES ('driver@surprise.me', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial, aware_of_assignment)" +
                        " VALUE ('driver@surprise.me', NULL, TRUE);");
        // WHEN
        Executable executable = () -> driverRepository.createDriver("driver@surprise.me");
        // THEN
        assertThrows(DriverCreationException.class, executable);
    }

    @Test
    void deleteDriver_shouldDeleteDriverFromTableBusDriver() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUES ('driver@surprise.me', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial, aware_of_assignment)" +
                        " VALUE ('driver@surprise.me', NULL, TRUE);");
        // WHEN
        driverRepository.deleteDriver(new Driver("driver@surprise.me", null, true));
        // THEN
        assertNull(driverRepository.findDriverByEmail("driver@surprise.me"));
    }
}