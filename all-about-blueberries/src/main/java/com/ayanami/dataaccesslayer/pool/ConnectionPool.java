package com.ayanami.dataaccesslayer.pool;

import com.ayanami.dataaccesslayer.config.HikariCPConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * create connection pool
 */
public class ConnectionPool {
    private static final DataSource dataSource;

    static {
        HikariConfig config = HikariCPConfig.getConfig();
        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    private ConnectionPool() {
    }
}