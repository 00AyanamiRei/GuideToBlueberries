package com.ayanami.dataaccesslayer.dao.impl;

import com.ayanami.dataaccesslayer.dao.RipeningPeriodDAO;
import com.ayanami.businesslogiclayer.model.RipeningPeriod;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RipeningPeriodDAOImpl implements RipeningPeriodDAO {
    private static final Logger LOGGER = LogManager.getLogger(RipeningPeriodDAOImpl.class);

    private final DataSource dataSource;

    public RipeningPeriodDAOImpl() {
        this.dataSource = ConnectionPool.getDataSource();
    }

    public RipeningPeriodDAOImpl(DataSource dataSource) {
        this.dataSource = ConnectionPool.getDataSource();
    }

    /**
     * save a new RipeningPeriod
     * @param ripeningPeriod
     */
    @Override
    public void save(@Valid RipeningPeriod ripeningPeriod) {
        String sql = "INSERT INTO ripening_periods (id, deadline) VALUES (?, ?)";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ripeningPeriod.getId());
            statement.setDate(2, new java.sql.Date(ripeningPeriod.getDeadline().getTime()));
            statement.executeUpdate();
            LOGGER.info("RipeningPeriod saved: {}", ripeningPeriod);
        } catch (SQLException e) {
            LOGGER.error("Error saving RipeningPeriod: {}", ripeningPeriod, e);
        }
    }

    /**
     * update a RipeningPeriod
     * @param ripeningPeriod
     */
    @Override
    public void update(@Valid RipeningPeriod ripeningPeriod) {
        String sql = "UPDATE ripening_periods SET deadline = ? WHERE id = ?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(ripeningPeriod.getDeadline().getTime()));
            statement.setInt(2, ripeningPeriod.getId());
            statement.executeUpdate();
            LOGGER.info("RipeningPeriod updated: {}", ripeningPeriod);
        } catch (SQLException e) {
            LOGGER.error("Error updating RipeningPeriod: {}", ripeningPeriod, e);
        }
    }

    /**
     * delete a RipeningPeriod
     * @param ripeningPeriod
     */
    @Override
    public void delete(@Valid RipeningPeriod ripeningPeriod) {
        String sql = "DELETE FROM ripening_periods WHERE id = ?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ripeningPeriod.getId());
            statement.executeUpdate();
            LOGGER.info("RipeningPeriod deleted: {}", ripeningPeriod);
        } catch (SQLException e) {
            LOGGER.error("Error deleting RipeningPeriod: {}", ripeningPeriod, e);
        }
    }

    /**
     * find a RipeningPeriod by ID
     * @param id
     * @return
     */
    @Override
    public RipeningPeriod findById(int id) {
        String sql = "SELECT * FROM ripening_periods WHERE id = ?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToRipeningPeriod(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding RipeningPeriod by ID: {}", id, e);
        }
        return null;
    }

    /**
     * find all RipeningPeriods
     * @return
     */
    @Override
    public List<RipeningPeriod> findAll() {
        String sql = "SELECT * FROM ripening_periods";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<RipeningPeriod> ripeningPeriods = new ArrayList<>();
            while (resultSet.next()) {
                RipeningPeriod ripeningPeriod = mapResultSetToRipeningPeriod(resultSet);
                ripeningPeriods.add(ripeningPeriod);
            }
            return ripeningPeriods;
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RipeningPeriods", e);
        }
        return Collections.emptyList();
    }

    /**
     * map ResultSet to RipeningPeriod
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private RipeningPeriod mapResultSetToRipeningPeriod(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        Date deadline = resultSet.getDate("deadline");

        return new RipeningPeriod(id, deadline);
    }
}
