package com.papenko.project;

import com.mysql.jdbc.Driver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import static java.lang.System.getenv;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class DataSourceHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceHolder.class);
    private static DataSource INSTANCE;

    static {
        final HikariConfig hikariConfig;
        if (isLocalEnv()) {
            LOGGER.warn("Local database is being used");
            hikariConfig = new HikariConfig("/db/connection-pool.properties");
        } else {
            LOGGER.warn("CI database is being used");
            hikariConfig = new HikariConfig();
            String username = getenv("MYSQL_USER");
            String password = getenv("MYSQL_PASSWORD");
            LOGGER.debug("CI database username[?] password[?]", username, password);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);
            hikariConfig.setDriverClassName(Driver.class.getName());
            hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        }
        INSTANCE = new HikariDataSource(hikariConfig);
        Flyway.configure()
                .locations("classpath:db/schema", "classpath:db/data")
                .dataSource(INSTANCE)
                .load()
                .migrate();
        LOGGER.debug("Initialized");
    }

    private static boolean isLocalEnv() {
        return isBlank(getenv("MYSQL_USER"));
    }

    public static DataSource getInstance() {
        return INSTANCE;
    }
}
