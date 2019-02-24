package com.papenko.project;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class DataSourceHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceHolder.class);
    private static final DataSource INSTANCE = new HikariDataSource(
            new HikariConfig("/db/connection-pool.properties")
    );

    static {
        LOGGER.debug("Initialized");
    }

    public static DataSource getInstance() {
        return INSTANCE;
    }
}
