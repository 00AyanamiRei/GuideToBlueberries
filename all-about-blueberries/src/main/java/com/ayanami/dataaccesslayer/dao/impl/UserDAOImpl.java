package com.ayanami.dataaccesslayer.dao.impl;

import com.ayanami.dataaccesslayer.dao.UserDAO;
import com.ayanami.businesslogiclayer.model.User;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    private DataSource dataSource;

    public UserDAOImpl() {
        this.dataSource = ConnectionPool.getDataSource();
    }

    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(@Valid User user) {
        // Валідація входного об'єкту User
        // Якщо валідація не пройшла успішно, буде викинуто виключення ConstraintViolationException
        // з описом всіх порушень валідації
        // Докладніше про валідацію можна дізнатися з документації Bean Validation API
        String sql = "INSERT INTO users (id, user_name, password, mail) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getMail());

            statement.executeUpdate();
            LOGGER.info("User saved: {}", user);
        } catch (SQLException e) {
            LOGGER.error("Error saving user: {}", user, e);
        }
    }

    @Override
    public void update(@Valid  User user) {
        String sql = "UPDATE users SET user_name = ?, password = ?, mail = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getMail());
            statement.setInt(4, user.getId());

            statement.executeUpdate();
            LOGGER.info("User updated: {}", user);
        } catch (SQLException e) {
            LOGGER.error("Error updating user: {}", user, e);
        }
    }

    @Override
    public void delete(@Valid User user) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());

            statement.executeUpdate();
            LOGGER.info("User deleted: {}", user);
        } catch (SQLException e) {
            LOGGER.error("Error deleting user: {}", user, e);
        }
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT id, user_name, password, mail FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = createUserFromResultSet(resultSet);
                    LOGGER.info("User found: {}", user);
                    return user;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding user by ID: {}", id, e);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, user_name, password, mail FROM users";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                userList.add(user);
            }
            LOGGER.info("All users retrieved. Count: {}", userList.size());
        } catch (SQLException e) {
            LOGGER.error("Error retrieving all users", e);
        }
        return userList;
    }

    // Допоміжний метод для створення об'єкту User з ResultSet
    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String user_name = resultSet.getString("userName");
        String password = resultSet.getString("password");
        String mail = resultSet.getString("mail");
        return new User(id, user_name, password, mail);
    }
}