package com.papenko.project.repository;

import com.papenko.project.entity.Driver;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScripts;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    void updateDriver_shouldUpdateBusOfDriver() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUES ('IA9669SA', '7L'), ('FI6669CT', '7L'); " +
                        "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUE ('bus.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial) VALUE ('bus.driver@yes', 'IA9669SA')");
        // WHEN
        driverRepository.updateDriver(new Driver("bus.driver@yes", "FI6669CT"));
        // THEN
        assertEquals("FI6669CT", driverRepository.findDriverByEmail("bus.driver@yes").getBusSerial());
    }

    @Test
    void findDriverByEmail_shouldReturnDriver_whenDriverWithSuchEmailExists() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L'); " +
                        "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUE ('bus.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial) VALUE ('bus.driver@yes', 'IA9669SA')");
        // WHEN
        Driver driver = driverRepository.findDriverByEmail("bus.driver@yes");
        // THEN
        assertEquals(new Driver("bus.driver@yes", "IA9669SA"), driver);
    }

    @Test
    void findDriverByEmail_shouldReturnNull_whenNoDriverExistsWithSuchEmail() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUE ('7L'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L'); " +
                        "INSERT INTO depot_user (email, user_type, password_hash)" +
                        " VALUE ('bus.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');" +
                        "INSERT INTO bus_driver (user_email, bus_serial) VALUE ('bus.driver@yes', 'IA9669SA')");
        // WHEN
        Driver driver = driverRepository.findDriverByEmail("hell.driver@yes");
        // THEN
        assertNull(driver);
    }
}