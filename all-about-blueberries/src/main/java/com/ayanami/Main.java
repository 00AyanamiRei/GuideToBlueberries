package com.ayanami;


import com.ayanami.businesslogiclayer.game.view.ViewManager;
import com.ayanami.businesslogiclayer.model.User;
import com.ayanami.dataaccesslayer.dao.UserDAO;
import com.ayanami.dataaccesslayer.dao.impl.UserDAOImpl;
import com.ayanami.presentationlayer.MessageBoxUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

/**
 * main class for starting the application
 */
public class Main extends Application {

       private double xOffset = 0.0;
       private double yOffset = 0.0;

       public Main() {
       }

       public void start(Stage primaryStage) throws Exception {
           Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("fxml/login/Main.fxml"));
           primaryStage.initStyle(StageStyle.TRANSPARENT);
           root.setOnMousePressed((event) -> {
               this.xOffset = event.getSceneX();
               this.yOffset = event.getSceneY();
           });
           root.setOnMouseDragged((event) -> {
               primaryStage.setX(event.getScreenX() - this.xOffset);
               primaryStage.setY(event.getScreenY() - this.yOffset);
           });
           Scene scene = new Scene(root);
           scene.setFill(Color.TRANSPARENT);
           primaryStage.setScene(scene);
           primaryStage.show();
       }

       public static void main(String[] args) {
           launch(args);
       }
}