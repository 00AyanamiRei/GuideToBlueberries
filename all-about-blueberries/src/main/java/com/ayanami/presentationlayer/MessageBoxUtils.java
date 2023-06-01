package com.ayanami.presentationlayer;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Window;

public class MessageBoxUtils {
    public static void showInformation(Window window, String title, String header, String message) {
        showAlert(window, title, header, message, AlertType.INFORMATION);
    }

    public static void showWarning(Window window, String title, String header, String message) {
        showAlert(window, title, header, message, AlertType.WARNING);
    }

    public static void showError(Window window, String title, String header, String message) {
        showAlert(window, title, header, message, AlertType.ERROR);
    }

    public static boolean showConfirmation(Window window, String title, String header, String message) {
        return showConfirmationAlert(window, title, header, message);
    }

    private static void showAlert(Window window, String title, String header, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.initOwner(window);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        // Налаштування стилів
        Region alertRoot = (Region) alert.getDialogPane().getChildren().get(0);
        alertRoot.setStyle("-fx-background-color: pink;"); // Задати фоновий колір

        Label messageLabel = (Label) alert.getDialogPane().lookup(".content.label");
        messageLabel.setStyle("-fx-text-fill: black;"); // Задати колір тексту

        alert.showAndWait();
    }

    private static boolean showConfirmationAlert(Window window, String title, String header, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(window);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        // Налаштування стилів
        Region alertRoot = (Region) alert.getDialogPane().getChildren().get(0);
        alertRoot.setStyle("-fx-background-color: pink;"); // Задати фоновий колір

        Label messageLabel = (Label) alert.getDialogPane().lookup(".content.label");
        messageLabel.setStyle("-fx-text-fill: black;"); // Задати колір тексту

        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}
