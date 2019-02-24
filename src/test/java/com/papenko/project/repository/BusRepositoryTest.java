package com.papenko.project.repository;

import com.papenko.project.entity.Bus;
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

class BusRepositoryTest {
    private BusRepository busRepository;
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
        Bus bus0 = new Bus("FI6669CT", "7L");
        Bus bus1 = new Bus("IA9669SA", "7L");
        assertEquals(List.of(bus0, bus1), busesSerials);
    }

    @Test
    void updateBus_shouldUpdateRouteOfBus() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO route (route_name) VALUES ('7L'), ('96'); " +
                        "INSERT INTO bus (bus_serial, route_name) VALUE ('IA9669SA', '7L');");
        // WHEN
        busRepository.updateBus(new Bus("IA9669SA", "96"));
        // THEN
        assertEquals(new Bus("IA9669SA", "96"), busRepository.findBusBySerialNumber("IA9669SA"));
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
        assertEquals(new Bus("IA9669SA", "7L"), bus);
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
}