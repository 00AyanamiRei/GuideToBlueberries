package com.ayanami.dataaccesslayer;

import com.ayanami.dataaccesslayer.pool.ConnectionPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ExampleClass {
    public void exampleMethod() throws SQLException {
        DataSource dataSource = ConnectionPool.getDataSource();
        Connection connection = dataSource.getConnection();
        // Використання з'єднання з базою даних
        connection.close(); // Повернення з'єднання до пулу
    }
}
