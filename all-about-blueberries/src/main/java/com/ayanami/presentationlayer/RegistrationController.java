package com.ayanami.presentationlayer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RegistrationController {
    @FXML
    private Label label;

    @FXML
    private Button button;

    @FXML
    private void handleButtonClick() {
        label.setText("Кнопка була натиснута ура аніме!");
    }
}
