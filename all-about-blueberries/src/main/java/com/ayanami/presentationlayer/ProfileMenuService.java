package com.ayanami.presentationlayer;

import com.ayanami.businesslogiclayer.ConfigManager;
import com.ayanami.businesslogiclayer.model.BlueberryReview;
import com.ayanami.dataaccesslayer.pool.ConnectionPool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.control.Label;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ProfileMenuService {
    @FXML
    ImageView ProfileImage;
    @FXML
    Label usernameLabel;
    @FXML
    ChoiceBox<String> choiceBoxBlueberry; // Змінено тип ChoiceBox на String
    @FXML
    ChoiceBox<Integer> choiceBoxRating;
    @FXML
    TextField textFieldReview;
    private static final Logger LOGGER = LogManager.getLogger(ProfileMenuService.class);
    private DataSource dataSource;

    @FXML
    private void initialize() {
        try {
            loadUserImage();
            usernameLabel.setText(ConfigManager.getUsername());
        } catch (IOException e) {
            LOGGER.error("Error: {}", e);
        }
        dataSource = ConnectionPool.getDataSource();
        loadDataBlueberry();
    }

    private void loadDataBlueberry() {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name FROM blueberry";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int blueberryID = resultSet.getInt("id");
                String blueberryName = resultSet.getString("name");
                String item = blueberryID + ": " + blueberryName;
                choiceBoxBlueberry.getItems().add(item);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @FXML
    private void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Вибрати зображення");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Зображення", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) ProfileImage.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Image selectedImage = new Image(selectedFile.toURI().toString());
            Circle profileImageCircle = new Circle(90, 90, 90);
            profileImageCircle.setFill(new ImagePattern(selectedImage));
            ProfileImage.setClip(profileImageCircle);
            ProfileImage.setImage(selectedImage);
            ConfigManager.saveImage(selectedImage.getUrl());
        }
    }

    public void loadUserImage() throws IOException {
        try {
            String imagePath = ConfigManager.getImagePath();
            Image image = ConfigManager.loadImage(imagePath);
            if (image != null) {
                Circle profileImageCircle = new Circle(90, 90, 90);
                profileImageCircle.setFill(new ImagePattern(image));
                ProfileImage.setClip(profileImageCircle);
                ProfileImage.setImage(image);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void sendButtonClicked(ActionEvent actionEvent) {
        try (Connection connection = dataSource.getConnection()) {
            String selectedBlueberry = choiceBoxBlueberry.getValue(); // Отримати вибраний елемент з ChoiceBox
            String reviewText = textFieldReview.getText();
            int rating = choiceBoxRating.getValue();

            int userID = getUserId();

            if (userID == -1) {
                MessageBoxUtils.showWarning(null, "Попередження", "Діалогове вікно попередження", "Користувача не знайдено");
                return;
            }

            // Розділіть значення blueberryID та blueberryName
            String[] blueberryData = selectedBlueberry.split(": ");
            int selectedBlueberryID = Integer.parseInt(blueberryData[0]);

            BlueberryReview blueberryReview = new BlueberryReview(selectedBlueberryID, userID, reviewText, rating);

            String query = "INSERT INTO blueberry_review (blueberry_id, user_id, Review, Rating) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, blueberryReview.getBlueberryID());
            statement.setInt(2, blueberryReview.getUserID());
            statement.setString(3, blueberryReview.getReview());
            statement.setInt(4, blueberryReview.getRating());
            statement.executeUpdate();
            MessageBoxUtils.showInformation(null, "Інформація", "Інформаційний діалог",String.format("Відгук про лохину збережено: %s", blueberryReview));
            choiceBoxBlueberry.setValue(null);
            choiceBoxRating.setValue(null);
            textFieldReview.setText(null);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private int getUserId() {
        try {
            String username = ConfigManager.getUsername();
            int userID = -1;

            try (Connection connection = dataSource.getConnection()) {
                String query = "SELECT id FROM users WHERE user_name = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    userID = resultSet.getInt("id");
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }

            return userID;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}