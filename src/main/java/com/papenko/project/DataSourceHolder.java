package com.papenko.project;

import com.mysql.cj.jdbc.Driver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.net.URI;

import static java.lang.System.getenv;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.split;

public class DataSourceHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceHolder.class);
    private static final String PRODUCTION_DB_URL_ENV_PROPERTY = "JAWSDB_URL";
    private static final String CI_DB_USER_ENV_PROPERTY = "MYSQL_USER";
    private static final DataSource DATA_SOURCE_INSTANCE;

    static {
        LOGGER.debug("about to initialize DATA_SOURCE_INSTANCE");
        final HikariConfig hikariConfig;
        final String dataScriptsDirectoryURL;
        if (isProduction()) {
            dataScriptsDirectoryURL = "classpath:db/production-data";
            LOGGER.warn("Production database is being used");
            hikariConfig = new HikariConfig();
            URI dbUri = URI.create(System.getenv(PRODUCTION_DB_URL_ENV_PROPERTY));
            String[] credentials = split(dbUri.getUserInfo(), ':');
            hikariConfig.setUsername(credentials[0]);
            hikariConfig.setPassword(credentials[1]);
            hikariConfig.setJdbcUrl("jdbc:mysql://" + dbUri.getHost() + dbUri.getPath());
        } else if (isCIEnv()) {
            dataScriptsDirectoryURL = "classpath:db/e2e-tests-data";
            LOGGER.warn("CI database is being used");
            hikariConfig = new HikariConfig();
            String username = getenv("MYSQL_USER");
            String password = getenv("MYSQL_PASSWORD");
            LOGGER.debug("CI database username: [{}] password: [{}]", username, password);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);
            hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        } else {
            dataScriptsDirectoryURL = "classpath:db/e2e-tests-data";
            LOGGER.warn("Local database is being used");
            hikariConfig = new HikariConfig("/db/connection-pool.properties");
        }
        hikariConfig.setDriverClassName(Driver.class.getName());
        DATA_SOURCE_INSTANCE = new HikariDataSource(hikariConfig);
        Flyway.configure()
                .locations("classpath:db/schema", dataScriptsDirectoryURL)
                .dataSource(DATA_SOURCE_INSTANCE)
                .load()
                .migrate();
        LOGGER.debug("Initialized");
    }

    private DataSourceHolder() {
    }

    private static boolean isProduction() {
        return isNotBlank(getenv(PRODUCTION_DB_URL_ENV_PROPERTY));
    }

    private static boolean isCIEnv() {
        return isNotBlank(getenv(CI_DB_USER_ENV_PROPERTY));
    }

    public static DataSource getInstance() {
        return DATA_SOURCE_INSTANCE;
    }
}
