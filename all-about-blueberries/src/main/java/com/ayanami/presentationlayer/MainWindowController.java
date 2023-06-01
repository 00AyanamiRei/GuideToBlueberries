package com.ayanami.presentationlayer;

import com.ayanami.businesslogiclayer.game.view.ViewManager;
import com.ayanami.dataaccesslayer.dao.UserDAO;
import com.ayanami.dataaccesslayer.dao.impl.UserDAOImpl;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static javax.swing.text.StyleConstants.setAlignment;


public class MainWindowController {
    @FXML
    private VBox cont;

    @FXML
    Button openAdminMenuButton;

    @FXML
    public void initialize() {

        cont.setFillWidth(true);
        cont.setAlignment(Pos.CENTER);
        obtainingUserStatus();
    }

    @FXML
    private void open_blueberries(ActionEvent event) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), cont);
        t.play();

        // Створення прогрес-бару
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS); // Встановлення індетермінованого прогресу
        progressBar.setPrefWidth(300); // Задайте ширину прогрес-бару відповідно до контейнера
        // Додавання класу стилів до прогрес-бара
        progressBar.getStylesheets().add(getClass().getResource("/com/ayanami/css/progressbarstyle.css").toExternalForm());
        // Додавання прогрес-бару до контейнера
        cont.getChildren().add(progressBar);



        t.setOnFinished((e) ->{
            try{
                Parent fxml = FXMLLoader.load(getClass().getResource("/com/ayanami/fxml/main/content/TableBlueberriesMenu.fxml"));
                cont.getChildren().removeAll();
                cont.getChildren().setAll(fxml);
            }catch(IOException ex) {
               System.out.println(ex.getMessage());
            }
        });
    }

    @FXML
    private void open_review(ActionEvent event) {
        TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), cont);
        t1.play();

        // Створення прогрес-бару
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS); // Встановлення ��ндетермінованого прВстановлення ��ндетермінованого прВстановлення ��ндетермінованого прВстановлення індетермінованого прогресу
        progressBar.setPrefWidth(300); // Задайте ширину прогрес-бару відповідно до контейнера
        // Додавання класу стилів до прогрес-бара
        progressBar.getStylesheets().add(getClass().getResource("/com/ayanami/css/progressbarstyle.css").toExternalForm());
        // Додавання прогрес-бару до контейнера
        cont.getChildren().add(progressBar);

        t1.setOnFinished((e) ->{
            try{
                Parent fxml = FXMLLoader.load(getClass().getResource("/com/ayanami/fxml/main/content/TableReviewMenu.fxml"));
                cont.getChildren().removeAll();
                cont.getChildren().setAll(fxml);
            }catch(IOException ex) {
                System.out.println(ex);
            }
        });
    }

    public void admin_menu_open(ActionEvent actionEvent) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), cont);
        t.play();

        // Створення прогрес-бару
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS); // Встановлення індетермінованого прогресу
        progressBar.setPrefWidth(300); // Задайте ширину прогрес-бару відповідно до контейнера
        // Додавання класу стилів до прогрес-бара
        progressBar.getStylesheets().add(getClass().getResource("/com/ayanami/css/progressbarstyle.css").toExternalForm());
        // Додавання прогрес-бару до контейнера
        cont.getChildren().add(progressBar);



        t.setOnFinished((e) ->{
            try{
                Parent fxml = FXMLLoader.load(getClass().getResource("/com/ayanami/fxml/main/content/AdminMenu.fxml"));
                cont.getChildren().removeAll();
                cont.getChildren().setAll(fxml);
            }catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    private void obtainingUserStatus() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/com/ayanami/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Отримання значень по ключам
        String username = properties.getProperty("username");
        UserDAOImpl userDao = new UserDAOImpl();
        String userStatus = userDao.getUserStatus(username);

        // Перевірка статусу користувача
        if (userStatus != null && userStatus.equals("admin")) {
            // Показати кнопку адміністратора
            openAdminMenuButton.setVisible(true);
        } else {
            // Приховати кнопку адміністратора
            openAdminMenuButton.setVisible(false);
        }
    }

    public void open_profile(ActionEvent actionEvent) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), cont);
        t.play();

        // Створення прогрес-бару
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS); // Встановлення індетермінованого прогресу
        progressBar.setPrefWidth(300); // Задайте ширину прогрес-бару відповідно до контейнера
        // Додавання класу стилів до прогрес-бара
        progressBar.getStylesheets().add(getClass().getResource("/com/ayanami/css/progressbarstyle.css").toExternalForm());
        // Додавання прогрес-бару до контейнера
        cont.getChildren().add(progressBar);



        t.setOnFinished((e) ->{
            try{
                Parent fxml = FXMLLoader.load(getClass().getResource("/com/ayanami/fxml/main/content/Profile.fxml"));
                cont.getChildren().removeAll();
                cont.getChildren().setAll(fxml);
            }catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    public void open_game(ActionEvent actionEvent) {
        try {
            Stage primaryStage;
            ViewManager manager = new ViewManager();
            primaryStage = manager.getMainStage();
            primaryStage.show();
            Stage stage = (Stage) cont.getScene().getWindow();
            stage.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}