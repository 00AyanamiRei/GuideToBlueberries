package com.ayanami.presentationlayer;

import com.ayanami.businesslogiclayer.ConfigManager;
import com.ayanami.dataaccesslayer.dao.impl.UserDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AuthService {
    @FXML
    private TextField UserNameField;

    @FXML
    private TextField PasswordField;
    @FXML
    private Button loginButton;

    String filePath = "src/main/resources/com/ayanami/config.properties";

    @FXML
    private void initialize() {
        loadDataLastAutorizationUser();
    }

    @FXML
    private void signin(ActionEvent event){
        String userName = UserNameField.getText();
        String password = PasswordField.getText();
        ConfigManager config = new ConfigManager();

        boolean confirmationResult = MessageBoxUtils.showConfirmation(null, "Підтвердження", "Діалогове вікно підтвердження", "Продовжити з цими даними?\r" + userName + "\r" + password);
        if (confirmationResult) {
             UserDAOImpl userDAO = new UserDAOImpl();
             boolean isAuthorized = userDAO.findUserByUserNameAndPassword(userName, password);

            if (isAuthorized) {
                config.saveUserInProperties(userName, password);
                config.createConfigFile(userName);
                closeCurrentScene();
                openMainScene();
            }
        }
    }

    private void closeCurrentScene() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
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

    private void loadDataLastAutorizationUser() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Отримання значень по ключам
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        // Встановлення значень по замовчуванню для текстових полів
        UserNameField.setText(username);
        PasswordField.setText(password);
    }
}