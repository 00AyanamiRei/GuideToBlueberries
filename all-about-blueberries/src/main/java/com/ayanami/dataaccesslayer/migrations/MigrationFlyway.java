package com.ayanami.dataaccesslayer.migrations;

import org.flywaydb.core.Flyway;

/**
 * migrates the database to the flyway
 */
public class MigrationFlyway {
    private static final String URL = "jdbc:mysql://containers-us-west-2.railway.app:6039/railway";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "bk2FIZTKfuBT85rsXk2A";

    public static void main(String[] args) {
        Flyway flyway =
                Flyway.configure().dataSource(URL, USERNAME, PASSWORD).locations("/db/migrations").load();
        flyway.baseline();
        flyway.migrate();
    }
}