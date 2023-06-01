package com.ayanami.dataaccesslayer.config;

import com.zaxxer.hikari.HikariConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Connection pool HikariCP
 */
public class HikariCPConfig {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/blueberry_data_hub";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "12345";

    private static final String POOL_NAME = "myHikariCP";

    private static final int MAXIMUM_POOL_SIZE = 10;
    private static final long CONNECTION_TIMEOUT_MS = 30000;
    private static final long IDLE_TIMEOUT_MS = 600000;

    private static final Logger LOGGER = LogManager.getLogger(HikariCPConfig.class);

    /**
     * HikariCP configuration create
     * @return config
     */
    public static HikariConfig getConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(JDBC_USERNAME);
        config.setPassword(JDBC_PASSWORD);
        config.setPoolName(POOL_NAME);
        config.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        config.setConnectionTimeout(CONNECTION_TIMEOUT_MS);
        config.setIdleTimeout(IDLE_TIMEOUT_MS);

        LOGGER.info("HikariCP configuration created: {}", config);

        return config;
    }
}
