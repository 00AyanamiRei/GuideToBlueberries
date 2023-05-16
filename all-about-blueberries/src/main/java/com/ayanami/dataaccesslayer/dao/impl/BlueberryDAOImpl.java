package com.ayanami.dataaccesslayer.dao.impl;

import com.ayanami.dataaccesslayer.dao.BlueberryDAO;
import com.ayanami.businesslogiclayer.model.Blueberry;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlueberryDAOImpl implements BlueberryDAO {
    private static final Logger LOGGER = LogManager.getLogger(BlueberryDAOImpl.class);

    @Override
    public void save(@Valid Blueberry blueberry) {
        String query = "INSERT INTO blueberry (id, name, size_blueberry_id, period_id, taste_id, climate_id, landing_distance, pollination, description, photo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, blueberry.getId());
            statement.setString(2, blueberry.getName());
            statement.setInt(3, blueberry.getSizeBlueberryID());
            statement.setInt(4, blueberry.getPeriodID());
            statement.setInt(5, blueberry.getTasteID());
            statement.setInt(6, blueberry.getClimateID());
            statement.setString(7, blueberry.getLandingDistance());
            statement.setString(8, blueberry.getPollination());
            statement.setString(9, blueberry.getDescription());
            statement.setString(10, blueberry.getPhoto());
            statement.executeUpdate();
            LOGGER.info("Blueberry saved: {}", blueberry);
        } catch (SQLException e) {
            LOGGER.error("Error saving blueberry: {}", blueberry, e);
        }
    }

    @Override
    public void update(@Valid Blueberry blueberry) {
        String query = "UPDATE blueberry SET name = ?, size_blueberry_id = ?, period_id = ?, taste_id = ?, " +
                "climate_id = ?, landing_distance = ?, pollination = ?, description = ?, photo = ? WHERE id = ?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, blueberry.getName());
            statement.setInt(2, blueberry.getSizeBlueberryID());
            statement.setInt(3, blueberry.getPeriodID());
            statement.setInt(4, blueberry.getTasteID());
            statement.setInt(5, blueberry.getClimateID());
            statement.setString(6, blueberry.getLandingDistance());
            statement.setString(7, blueberry.getPollination());
            statement.setString(8, blueberry.getDescription());
            statement.setString(9, blueberry.getPhoto());
            statement.setInt(10, blueberry.getId());
            statement.executeUpdate();
            LOGGER.info("Blueberry updated: {}", blueberry);
        } catch (SQLException e) {
            LOGGER.error("Error updating blueberry: {}", blueberry, e);
        }
    }

    @Override
    public void delete(@Valid Blueberry blueberry) {
        String query = "DELETE FROM blueberry WHERE id = ?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, blueberry.getId());
            statement.executeUpdate();
            LOGGER.info("Blueberry deleted: {}", blueberry);
        } catch (SQLException e) {
            LOGGER.error("Error deleting blueberry: {}", blueberry, e);
        }
    }

    @Override
    public Blueberry findById(int id) {
        Blueberry blueberry = null;
        String query = "SELECT * FROM blueberry WHERE id = ?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                blueberry = extractBlueberryFromResultSet(resultSet);
                LOGGER.info("Blueberry found by id: {}", blueberry);
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding blueberry by id: {}", id, e);
        }
        return blueberry;
    }

    @Override
    public List<Blueberry> findAll() {
        List<Blueberry> blueberries = new ArrayList<>();
        String query = "SELECT * FROM blueberry";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Blueberry blueberry = extractBlueberryFromResultSet(resultSet);
                blueberries.add(blueberry);
            }
            LOGGER.info("All blueberries found: {}", blueberries);
        } catch (SQLException e) {
            LOGGER.error("Error finding all blueberries", e);
        }
        return blueberries;
    }

    private Blueberry extractBlueberryFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int sizeBlueberryID = resultSet.getInt("size_blueberry_id");
        int periodID = resultSet.getInt("period_id");
        int tasteID = resultSet.getInt("taste_id");
        int climateID = resultSet.getInt("climate_id");
        String landingDistance = resultSet.getString("landing_distance");
        String pollination = resultSet.getString("pollination");
        String description = resultSet.getString("description");
        String photo = resultSet.getString("photo");

        return new Blueberry(id, name, sizeBlueberryID, periodID, tasteID, climateID, landingDistance, pollination, description, photo);
    }
}