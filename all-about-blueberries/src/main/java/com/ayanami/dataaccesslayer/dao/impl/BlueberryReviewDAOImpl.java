package com.ayanami.dataaccesslayer.dao.impl;

import com.ayanami.dataaccesslayer.dao.BlueberryReviewDAO;
import com.ayanami.businesslogiclayer.model.BlueberryReview;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlueberryReviewDAOImpl implements BlueberryReviewDAO {
    private static final Logger LOGGER = LogManager.getLogger(BlueberryReviewDAOImpl.class);

    private static final String SAVE_QUERY = "INSERT INTO blueberry_reviews (blueberry_id, user_id, review, rating) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE blueberry_reviews SET blueberry_id = ?, user_id = ?, review = ?, rating = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM blueberry_reviews WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM blueberry_reviews WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM blueberry_reviews";
    private final DataSource dataSource;

    public BlueberryReviewDAOImpl() {
        this.dataSource = ConnectionPool.getDataSource();
    }
    
    public BlueberryReviewDAOImpl(DataSource dataSource) {
        this.dataSource = ConnectionPool.getDataSource();
    }

    /**
     * save review user
     * @param review
     */
    @Override
    public void save(@Valid BlueberryReview review) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setInt(1, review.getBlueberryID());
            statement.setInt(2, review.getUserID());
            statement.setString(3, review.getReview());
            statement.setInt(4, review.getRating());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to save BlueberryReview", e);
        }
    }

    /**
     * update review user
     * @param review
     */
    @Override
    public void update(@Valid BlueberryReview review) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, review.getBlueberryID());
            statement.setInt(2, review.getUserID());
            statement.setString(3, review.getReview());
            statement.setInt(4, review.getRating());
            statement.setInt(5, review.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to update BlueberryReview", e);
        }
    }

    /**
     * delete review user
     * @param review
     */
    @Override
    public void delete(@Valid BlueberryReview review) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, review.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to delete BlueberryReview", e);
        }
    }

    /**
     * find review by id
     * @param id
     * @return
     */
    @Override
    public BlueberryReview findById(int id) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToReview(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find BlueberryReview by ID", e);
        }

        return null;
    }

    /**
     * find all reviews
     * @return
     */
    @Override
    public List<BlueberryReview> findAll() {
        List<BlueberryReview> reviews = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                BlueberryReview review = mapResultSetToReview(resultSet);
                reviews.add(review);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve BlueberryReviews", e);
        }

        return reviews;
    }

    /**
     * map ResultSet to BlueberryReview
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private BlueberryReview mapResultSetToReview(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int blueberryID = resultSet.getInt("blueberry_id");
        int userID = resultSet.getInt("user_id");
        String reviewText = resultSet.getString("review");
        int rating = resultSet.getInt("rating");

        return new BlueberryReview(id, blueberryID, userID, reviewText, rating);
    }
}

