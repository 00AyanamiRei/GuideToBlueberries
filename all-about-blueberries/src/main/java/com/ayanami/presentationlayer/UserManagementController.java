/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayanami.presentationlayer;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;

/**
 *
 * @author Admin
 */
public class UserManagementController implements Initializable {
     
    @FXML
    private VBox vbox;
    @FXML
    private AnchorPane anchorPane;
    private Parent fxml;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1.5), vbox);
        t.setToX(vbox.getLayoutX() * 23.2);
        t.play();
        t.setOnFinished((e) ->{
            try{
                fxml = FXMLLoader.load(getClass().getResource("/com/ayanami/fxml/login/SignIn.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ex){
                
            }
        });

        setupDraggableWindow();
    }

    @FXML
    private void open_signin(ActionEvent event){
          TranslateTransition t = new TranslateTransition(Duration.seconds(1.5), vbox);
        t.setToX(vbox.getLayoutX() * 23.2);
        t.play();
        t.setOnFinished((e) ->{
            try{
                fxml = FXMLLoader.load(getClass().getResource("/com/ayanami/fxml/login/SignIn.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ex){
                
            }
        });
    }

    @FXML
    private void open_signup(ActionEvent event){
          TranslateTransition t = new TranslateTransition(Duration.seconds(1.5), vbox);
        t.setToX(0);
        t.play();
        t.setOnFinished((e) ->{
            try{
                fxml = FXMLLoader.load(getClass().getResource("/com/ayanami/fxml/login/SignUp.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ex){
                
            }
        });
    }

    private void setupDraggableWindow() {
        anchorPane.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        anchorPane.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}