package com.ayanami.pool;

import com.ayanami.config.HikariCPConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

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