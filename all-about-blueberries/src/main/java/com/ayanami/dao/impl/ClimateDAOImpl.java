package com.ayanami.dao.impl;

import com.ayanami.dao.ClimateDAO;
import com.ayanami.model.Climate;
import com.ayanami.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClimateDAOImpl implements ClimateDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClimateDAOImpl.class);

    @Override
    public void save(Climate climate) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO climates (id, climate) VALUES (?, ?)")) {

            statement.setInt(1, climate.getId());
            statement.setString(2, climate.getClimate());

            statement.executeUpdate();
            LOGGER.info("Climate saved: {}", climate);

        } catch (SQLException e) {
            LOGGER.error("Error saving climate: {}", climate, e);
        }
    }

    @Override
    public void update(Climate climate) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE climates SET climate = ? WHERE id = ?")) {

            statement.setString(1, climate.getClimate());
            statement.setInt(2, climate.getId());

            statement.executeUpdate();
            LOGGER.info("Climate updated: {}", climate);

        } catch (SQLException e) {
            LOGGER.error("Error updating climate: {}", climate, e);
        }
    }

    @Override
    public void delete(Climate climate) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM climates WHERE id = ?")) {

            statement.setInt(1, climate.getId());

            statement.executeUpdate();
            LOGGER.info("Climate deleted: {}", climate);

        } catch (SQLException e) {
            LOGGER.error("Error deleting climate: {}", climate, e);
        }
    }

    @Override
    public Climate findById(int id) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM climates WHERE id = ?")) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Climate climate = createClimateFromResultSet(resultSet);
                    LOGGER.info("Climate found: {}", climate);
                    return climate;
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error finding climate by ID: {}", id, e);
        }

        LOGGER.info("Climate not found for ID: {}", id);
        return null;
    }

    @Override
    public List<Climate> findAll() {
        List<Climate> climates = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM climates")) {

            while (resultSet.next()) {
                Climate climate = createClimateFromResultSet(resultSet);
                climates.add(climate);
            }

        } catch (SQLException e) {
            LOGGER.error("Error finding all climates", e);
        }

        LOGGER.info("Found {} climates", climates.size());
        return climates;
    }

    private Climate createClimateFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String climate = resultSet.getString("climate");
        return Climate.builder()
                .id(id)
                .climate(climate)
                .build();
    }
}
