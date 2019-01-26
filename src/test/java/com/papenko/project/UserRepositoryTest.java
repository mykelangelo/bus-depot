package com.papenko.project;

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

class UserRepositoryTest {
    private UserRepository userRepository;
    private EmbeddedMysql embeddedMysql;

    @BeforeEach
    void setUp() {
        embeddedMysql = anEmbeddedMysql(
                aMysqldConfig(v5_7_latest)
                        .withUser("depot_user", "depot_password")
                        .withPort(3306)
                        .build()
        ).addSchema("depot_database", classPathScripts("db/migration/*.sql")
        ).start();

        userRepository = new UserRepository(
                new HikariDataSource(
                        new HikariConfig("/db/connection-pool.properties")
                )
        );
    }

    @AfterEach
    void shutDown() {
        embeddedMysql.stop();
    }

    @Test
    void findUserPasswordHashByEmail_shouldReturnPasswordHashOfUser_whenUserExistsWithGivenEmail() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO depot_user (email, user_type, password_hash) " +
                        "VALUES ('existing_user_email@yes', 'driver', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');");
        // WHEN
        String passwordHash = userRepository.findUserPasswordHashByEmail("existing_user_email@yes");
        // THEN
        assertEquals("$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG", passwordHash);
    }

    @Test
    void findUserPasswordHashByEmail_shouldReturnNull_whenNoUserExistsWithGivenEmail() {
        // GIVEN
        // WHEN
        String passwordHash = userRepository.findUserPasswordHashByEmail("non_existing_user_email@nope");
        // THEN
        assertNull(passwordHash);
    }

    @Test
    void findUserTypeByEmail_shouldReturnAdmin_whenAdminsEmailIsGiven() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO depot_user (email, user_type, password_hash) " +
                        "VALUES ('depot.admin@yes', 'admin', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');");
        // WHEN
        String userType = userRepository.findUserTypeByEmail("depot.admin@yes");
        // THEN
        assertEquals("admin", userType);
    }

    @Test
    void findUserTypeByEmail_shouldReturnDriver_whenDriversEmailIsGiven() {
        // GIVEN
        embeddedMysql.executeScripts("depot_database",
                () -> "INSERT INTO depot_user (email, user_type, password_hash) " +
                        "VALUES ('bus.driver@yes', 'driver', '$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG');");
        // WHEN
        String userType = userRepository.findUserTypeByEmail("bus.driver@yes");
        // THEN
        assertEquals("driver", userType);
    }
}