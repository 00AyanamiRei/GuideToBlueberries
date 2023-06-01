package com.ayanami.dataaccesslayer.dao.impl;

import com.ayanami.dataaccesslayer.exceptions.UserExistsException;
import com.ayanami.dataaccesslayer.dao.UserDAO;
import com.ayanami.businesslogiclayer.model.User;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import com.ayanami.presentationlayer.MessageBoxUtils;
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

    /**
     * save user
     * @param user
     * @return result save or not user
     */
    @Override
    public boolean save(@Valid User user) {
        String checkIfExistsQuery = "SELECT COUNT(*) FROM users WHERE user_name = ?";
        String insertUserQuery = "INSERT INTO users (user_name, password, mail) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement checkIfExistsStatement = connection.prepareStatement(checkIfExistsQuery);
             PreparedStatement insertUserStatement = connection.prepareStatement(insertUserQuery)) {
            // Перевірка наявності користувача з новим логіном
            checkIfExistsStatement.setString(1, user.getUserName());
            ResultSet resultSet = checkIfExistsStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                // Користувач з таким логіном вже існує, можна виконати відповідні дії або викинути виключення
                // Наприклад, можна викинути виключення з власним повідомленням
                LOGGER.info("User with username '{}' already exists and failed validation check", user.getUserName());
                MessageBoxUtils.showWarning(null, "Попередження", "Діалогове вікно попередження", String.format("Користувача з таким логіном вже існує: \rім'я користувача: %s", user.getUserName()));
                return false;
            }
            // Вставка нових даних користувача
            insertUserStatement.setString(1, user.getUserName());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.setString(3, user.getMail());
            insertUserStatement.executeUpdate();
            LOGGER.info("User saved: {}", user);
            MessageBoxUtils.showInformation(null, "Інформація", "Інформаційний діалог",String.format("Реєстрація успішна:\rзбережені дані: %s", user));
            return true;
        } catch (SQLException e) {
            LOGGER.error("Error saving user: {}", user, e);
            MessageBoxUtils.showWarning(null, "Попередження", "Діалогове вікно попередження", String.format("Користувача не збережено: \rдані: %s\r помилка: %s", user, e.getMessage()));
            return false;
        }
    }

    /**
     * update user
     * @param user
     */
    @Override
    public void update(@Valid  User user) {
        String sql = "UPDATE users SET user_name = ?, password = ?, mail = ? WHERE user_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getMail());

            statement.executeUpdate();
            LOGGER.info("User updated: {}", user);
        } catch (SQLException e) {
            LOGGER.error("Error updating user: {}", user, e);
        }
    }

    /**
     * delete user
     * @param user
     */
    @Override
    public void delete(@Valid User user) {
        String sql = "DELETE FROM users WHERE user_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserName());

            statement.executeUpdate();
            LOGGER.info("User deleted: {}", user);
        } catch (SQLException e) {
            LOGGER.error("Error deleting user: {}", user, e);
        }
    }

    /**
     * find user by id
     * @param id
     * @return user or null
     */
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

    /**
     * find all user
     * @return user or null
     */
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

    /**
     * find user by username and password
     * @param userNameInput and passwordInput
     * @return return result
     */
    @Override
    public boolean findUserByUserNameAndPassword(String userNameInput, String passwordInput) {
        String username = userNameInput;
        String password = passwordInput;

        try (Connection connection = dataSource.getConnection()){

            // Prepare the SQL statement
            String sql = "SELECT * FROM users WHERE user_name = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result
            if (resultSet.next()) {
                String foundUsername = resultSet.getString("user_name");
                String foundPassword = resultSet.getString("password");
                // Additional user information retrieval can be done here
                LOGGER.info(String.format("User found: user name: %s, password: %s" ,foundUsername ,foundPassword));
                MessageBoxUtils.showInformation(null, "Інформація", "Інформаційний діалог",String.format("Авторизація успішна:\rім'я користувача: %s\rпароль: %s", foundUsername, foundPassword));
                return true;
            } else {
                LOGGER.info(String.format("User not found: user name: %s, password: %s", username , password));
                MessageBoxUtils.showWarning(null, "Попередження", "Діалогове вікно попередження", String.format("Користувача не знайдено: \rім'я користувача: %s\rпароль: %s", username , password));
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getUserStatus(String username) {
        String status = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT status FROM users WHERE user_name = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                status = resultSet.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    // Допоміжний метод для створення об'єкту User з ResultSet
    /**
     * create user from result set
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String user_name = resultSet.getString("userName");
        String password = resultSet.getString("password");
        String mail = resultSet.getString("mail");
        String status = resultSet.getString("status");
        return new User(user_name, password, mail, status);
    }
}