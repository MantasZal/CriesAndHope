package org.example.criesandhope.utils;

import javafx.scene.control.Alert;

public class StandartDialogs {
    public void errorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Kas cia do");
        alert.setContentText(message);

        alert.showAndWait();
    }
    public void informationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();

    }
}
