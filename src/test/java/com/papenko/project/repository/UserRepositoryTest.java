package com.papenko.project.repository;

import com.papenko.project.entity.User;
import com.papenko.project.entity.UserType;
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

class UserRepositoryTest {
    private UserRepository userRepository;
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

        userRepository = new UserRepository(
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
    void findUserByEmail_shouldReturnUser_whenUserExistsWithGivenEmail() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO depot_user (email, user_type, password_hash) " +
                        "VALUES ('existing_user_email@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');");
        // WHEN
        User user = userRepository.findUserByEmail("existing_user_email@yes");
        // THEN
        assertEquals(new User("existing_user_email@yes", UserType.BUS_DRIVER,
                "$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG"), user);
    }
    @Test
    void findUserByEmail_shouldReturnNull_whenUserDoesNotExistsWithGivenEmail() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO depot_user (email, user_type, password_hash) " +
                        "VALUES ('existing_user_email@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');");
        // WHEN
        User user = userRepository.findUserByEmail("non_existing_user_email@yes");
        // THEN
        assertNull(user);
    }

    @Test
    void findAllDrivers_shouldReturnListWithAllDrivers() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO depot_user (email, user_type, password_hash) VALUES " +
                        "('bob.driver@yes',  'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG')," +
                        "('alex.driver@yes', 'BUS_DRIVER', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');");
        // WHEN
        List<User> driverUsers = userRepository.findAllDrivers();
        // THEN
        User driverUser0 = new User("alex.driver@yes", UserType.BUS_DRIVER, "$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG");
        User driverUser1 = new User("bob.driver@yes", UserType.BUS_DRIVER, "$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG");
        assertEquals(List.of(driverUser0, driverUser1), driverUsers);
    }
}