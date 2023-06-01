package com.ayanami.businesslogiclayer.game.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ayanami.businesslogiclayer.game.model.SHIP;
import com.ayanami.businesslogiclayer.game.model.InfoLabel;
import com.ayanami.businesslogiclayer.game.model.ShipPicker;
import com.ayanami.businesslogiclayer.game.model.SpaceRunnerButton;
import com.ayanami.businesslogiclayer.game.model.SpaceRunnerSubScene;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ViewManager {
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private final static int MENU_BUTTON_START_X = 100;
    private final static int MENU_BUTTON_START_Y = 150;


    private SpaceRunnerSubScene creditsSubscene;
    private SpaceRunnerSubScene helpSubscene;
    private SpaceRunnerSubScene scoreSubscene;
    private SpaceRunnerSubScene shipChooserSubscene;

    private SpaceRunnerSubScene sceneToHide;




    List<SpaceRunnerButton> menuButtons;

    List<ShipPicker> shipsList;
    private SHIP choosenShip;

    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT );
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createBackground();
        handle();
    }


    private void showSubScene(SpaceRunnerSubScene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }




    private void createSubScenes() {

        creditsSubscene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(creditsSubscene);
        helpSubscene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(helpSubscene);
        scoreSubscene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(scoreSubscene);


        createShipChooserSubScene();

    }


    private void createShipChooserSubScene() {
        shipChooserSubscene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(shipChooserSubscene);

        InfoLabel chooseShipLabel = new InfoLabel("Оберіть скін");
        chooseShipLabel.setLayoutX(110);
        chooseShipLabel.setLayoutY(25);
        shipChooserSubscene.getPane().getChildren().add(chooseShipLabel);
        shipChooserSubscene.getPane().getChildren().add(createShipsToChoose());
        shipChooserSubscene.getPane().getChildren().add(createButtonToStart());
        shipChooserSubscene.getPane().getChildren().add(createButtonToExit());



    }

    private HBox createShipsToChoose() {
        HBox box = new HBox();
        box.setSpacing(60);
        shipsList = new ArrayList<>();
        for (SHIP ship : SHIP.values()) {
            ShipPicker shipToPick = new ShipPicker(ship);
            shipsList.add(shipToPick);
            box.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    for (ShipPicker ship : shipsList) {
                        ship.setIsCircleChoosen(false);
                    }
                    shipToPick.setIsCircleChoosen(true);
                    choosenShip = shipToPick.getShip();

                }
            });
        }

        box.setLayoutX(300 - (118*2));
        box.setLayoutY(200);
        return box;
    }

    private SpaceRunnerButton createButtonToStart() {
        SpaceRunnerButton startButton = new SpaceRunnerButton("Почати");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (choosenShip != null) {
                    GameViewManager gameManager = new GameViewManager();
                    gameManager.createNewGame(mainStage, choosenShip);
                }
            }
        });

        return startButton;
    }

    private  SpaceRunnerButton createButtonToExit() {
        SpaceRunnerButton exitButton = new SpaceRunnerButton("Вийти в головне меню");
        exitButton.setLayoutX(350);
        exitButton.setLayoutY(235);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Додатковий функціонал для кнопки "Вийти"
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ayanami/fxml/main/MainWindow.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));
                    stage.show();
                    Stage gameStage = (Stage) exitButton.getScene().getWindow();
                    gameStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return exitButton;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void AddMenuButtons(SpaceRunnerButton button) {
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    public void handle() {
        showSubScene(shipChooserSubscene);
    }

    private void createBackground() {
        Image backgroundImage = new Image("/com/ayanami/images/background.png", mainPane.getWidth(), mainPane.getHeight(), false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }
}