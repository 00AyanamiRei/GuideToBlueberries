package com.ayanami.dataaccesslayer.dao.impl;

import com.ayanami.dataaccesslayer.dao.SizeBlueberryDAO;
import com.ayanami.businesslogiclayer.model.SizeBlueberry;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SizeBlueberryDAOImpl implements SizeBlueberryDAO
{
    private static final Logger LOGGER = LogManager.getLogger(SizeBlueberryDAOImpl.class);

    private static final String INSERT_QUERY = "INSERT INTO size_blueberry (id, size) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE size_blueberry SET size = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM size_blueberry WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT id, size FROM size_blueberry WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT id, size FROM size_blueberry";

    private DataSource dataSource;

    public SizeBlueberryDAOImpl() {
        this.dataSource = ConnectionPool.getDataSource();
    }

    public SizeBlueberryDAOImpl(DataSource dataSource) {
        this.dataSource = ConnectionPool.getDataSource();
    }

    /**
     * save a new SizeBlueberry
     * @param sizeBlueberry
     */
    @Override
    public void save(@Valid SizeBlueberry sizeBlueberry) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {

            statement.setInt(1, sizeBlueberry.getId());
            statement.setString(2, sizeBlueberry.getSize());
            statement.executeUpdate();

            LOGGER.info("SizeBlueberry saved successfully: {}", sizeBlueberry);
        } catch (SQLException e) {
            LOGGER.error("Error saving SizeBlueberry: {}", sizeBlueberry, e);
        }
    }

    /**
     * update a SizeBlueberry
     * @param sizeBlueberry
     */
    @Override
    public void update(@Valid SizeBlueberry sizeBlueberry) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, sizeBlueberry.getSize());
            statement.setInt(2, sizeBlueberry.getId());
            statement.executeUpdate();

            LOGGER.info("SizeBlueberry updated successfully: {}", sizeBlueberry);
        } catch (SQLException e) {
            LOGGER.error("Error updating SizeBlueberry: {}", sizeBlueberry, e);
        }
    }

    /**
     * delete a SizeBlueberry
     * @param sizeBlueberry
     */
    @Override
    public void delete(@Valid SizeBlueberry sizeBlueberry) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, sizeBlueberry.getId());
            statement.executeUpdate();

            LOGGER.info("SizeBlueberry deleted successfully: {}", sizeBlueberry);
        } catch (SQLException e) {
            LOGGER.error("Error deleting SizeBlueberry: {}", sizeBlueberry, e);
        }
    }

    /**
     * find a SizeBlueberry by id
     * @param id
     * @return
     */
    @Override
    public SizeBlueberry findById(int id) {
        SizeBlueberry sizeBlueberry = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String size = resultSet.getString("size");
                sizeBlueberry = new SizeBlueberry(id, size);
            }

            LOGGER.info("SizeBlueberry found by id {}: {}", id, sizeBlueberry);
        } catch (SQLException e) {
            LOGGER.error("Error finding SizeBlueberry by id: {}", id, e);
        }

        return sizeBlueberry;
    }

    /**
     * find all SizeBlueberries
     * @return
     */
    @Override
    public List<SizeBlueberry> findAll() {
        List<SizeBlueberry> sizeBlueberries = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String size = resultSet.getString("size");
                SizeBlueberry sizeBlueberry = new SizeBlueberry(id, size);
                sizeBlueberries.add(sizeBlueberry);
            }

            LOGGER.info("All SizeBlueberries found: {}", sizeBlueberries);
        } catch (SQLException e) {
            LOGGER.error("Error finding all SizeBlueberries", e);
        }

        return sizeBlueberries;
    }

}
