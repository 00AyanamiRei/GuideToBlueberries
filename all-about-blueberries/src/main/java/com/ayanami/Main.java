package com.ayanami;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.psl.PublicSuffixMatcherLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("presentationlayer/Registration.fxml")));
        primaryStage.setTitle("Hello JavaFX");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }
}
