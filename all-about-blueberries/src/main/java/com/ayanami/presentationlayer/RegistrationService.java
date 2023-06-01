package com.ayanami.presentationlayer;

import com.ayanami.businesslogiclayer.ConfigManager;
import com.ayanami.businesslogiclayer.model.User;
import com.ayanami.businesslogiclayer.validation.UserValidator;
import com.ayanami.dataaccesslayer.dao.impl.UserDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationService {
    @FXML
    private TextField userNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwrodField;
    @FXML
    Button closeButton = new Button();
    @FXML
    Button registrationButton;

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    @FXML
    private void signup(ActionEvent event) throws IOException {
        String userName = userNameField.getText();
        String email = emailField.getText();
        String password = passwrodField.getText();
        ConfigManager config = new ConfigManager();

        User user = User.builder()
                .userName(userName) // Задайте відповідне ім'я користувача
                .password(password) // Задайте відповідний пароль користувача
                .mail(email)
                .status("user")// Задайте відповідну електронну пошту користувача
                .build();

        boolean isUsernameValid = UserValidator.validateUsername(user.getUserName());
        boolean isEmailValid = UserValidator.validateEmail(user.getMail());
        boolean isPasswordValid = UserValidator.validatePassword(user.getPassword());

        if (isUsernameValid && isEmailValid && isPasswordValid) {
            LOGGER.info("Validating user: {}", user);
            UserDAOImpl userDAO = new UserDAOImpl();
            boolean result = userDAO.save(user);

            if (result) {
                config.createConfigFile(userName);
                config.saveUserInProperties(userName, password);
                closeCurrentScene();
                openMainScene();
            }
        }
        else {
            LOGGER.error("Invalid user: {}", user);
            MessageBoxUtils.showWarning(null, "Попередження", "Діалогове вікно попередження", String.format("Дані не валідні: \rдані: %s", user));
        }
    }

    private void closeCurrentScene() {
        Stage stage = (Stage) registrationButton.getScene().getWindow();
        stage.close();
    }

    private void openMainScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ayanami/fxml/main/MainWindow.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}