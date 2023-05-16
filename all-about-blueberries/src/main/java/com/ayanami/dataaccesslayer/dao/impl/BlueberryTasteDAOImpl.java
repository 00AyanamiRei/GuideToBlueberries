package com.ayanami.dataaccesslayer.dao.impl;

import com.ayanami.dataaccesslayer.dao.BlueberryTasteDAO;
import com.ayanami.businesslogiclayer.model.BlueberryTaste;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlueberryTasteDAOImpl implements BlueberryTasteDAO {
    private static final Logger LOGGER = LogManager.getLogger(BlueberryTasteDAOImpl.class);
    private static final String SAVE_QUERY = "INSERT INTO blueberry_taste (id, taste) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE blueberry_taste SET taste = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM blueberry_taste WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM blueberry_taste WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM blueberry_taste";

    private final DataSource dataSource;

    public BlueberryTasteDAOImpl() {
        this.dataSource = ConnectionPool.getDataSource();
    }

    @Override
    public void save(@Valid BlueberryTaste blueberryTaste) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setInt(1, blueberryTaste.getId());
            statement.setString(2, blueberryTaste.getTaste());
            statement.executeUpdate();
            LOGGER.info("BlueberryTaste saved: {}", blueberryTaste);
        } catch (SQLException e) {
            LOGGER.error("Error saving BlueberryTaste: {}", blueberryTaste, e);
        }
    }

    @Override
    public void update(@Valid BlueberryTaste blueberryTaste) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, blueberryTaste.getTaste());
            statement.setInt(2, blueberryTaste.getId());
            statement.executeUpdate();
            LOGGER.info("BlueberryTaste updated: {}", blueberryTaste);
        } catch (SQLException e) {
            LOGGER.error("Error updating BlueberryTaste: {}", blueberryTaste, e);
        }
    }

    @Override
    public void delete(@Valid BlueberryTaste blueberryTaste) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, blueberryTaste.getId());
            statement.executeUpdate();
            LOGGER.info("BlueberryTaste deleted: {}", blueberryTaste);
        } catch (SQLException e) {
            LOGGER.error("Error deleting BlueberryTaste: {}", blueberryTaste, e);
        }
    }

    @Override
    public BlueberryTaste findById(int id) {
        BlueberryTaste blueberryTaste = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int foundId = resultSet.getInt("id");
                    String taste = resultSet.getString("taste");
                    blueberryTaste = new BlueberryTaste(foundId, taste);
                }
            } catch (SQLException e) {
                LOGGER.error("Error finding BlueberryTaste with id {}: {}", id, e);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting connection for finding BlueberryTaste with id {}: {}", id, e);
        }
        return blueberryTaste;
    }

    @Override
    public List<BlueberryTaste> findAll() {
        List<BlueberryTaste> blueberryTastes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String taste = resultSet.getString("taste");
                BlueberryTaste blueberryTaste = new BlueberryTaste(id, taste);
                blueberryTastes.add(blueberryTaste);
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding all BlueberryTastes", e);
        }
        return blueberryTastes;
    }
}
