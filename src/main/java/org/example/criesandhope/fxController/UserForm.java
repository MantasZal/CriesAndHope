package org.example.criesandhope.fxController;

import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class UserForm {
    public RadioButton userRadio;
    public RadioButton driverRadio;
    public RadioButton restaurantRadio;
    public RadioButton clientRadio;
    public TextField nameField;
    public TextField surnameField;
    public TextField phoneNumberField;
    public TextField loginNameField;
    public TextField passwordField;
    public TextField addressField;

    public void disableFields(ActionEvent actionEvent) {
        if (userRadio.isSelected()) {
            addressField.setDisable(true);
        } else if (restaurantRadio.isSelected()) {
            restaurantRadio.setSelected(true);
            addressField.setDisable(false);
        } else if (clientRadio.isSelected()) {
            clientRadio.setSelected(true);
            addressField.setDisable(false);
        } else if (driverRadio.isSelected()) {
            driverRadio.setSelected(true);
            addressField.setDisable(false);
        } else {
        }
    }
}
