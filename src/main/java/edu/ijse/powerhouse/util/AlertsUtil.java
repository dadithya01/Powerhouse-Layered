package edu.ijse.powerhouse.util;

import javafx.scene.control.Alert;

public class AlertsUtil {
    public static void showWarnerAlert(String message) {
        new Alert(Alert.AlertType.WARNING, message).show();
    }

    public static void showErrorAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    public static void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.setTitle("Success!");
        alert.setHeaderText("Success!");
        alert.show();
    }
}
