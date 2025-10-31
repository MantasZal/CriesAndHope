package org.example.criesandhope.fxController;

import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.example.criesandhope.hibernateControl.GenericHibernate;
import org.example.criesandhope.model.BasicUser;
import org.example.criesandhope.model.Restaurant;
import org.example.criesandhope.model.User;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UserForm implements Initializable {
    @FXML
    public RadioButton userRadio;
    @FXML
    public RadioButton driverRadio;
    @FXML
    public RadioButton restaurantRadio;
    @FXML
    public RadioButton clientRadio;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField phoneNumberField;
    @FXML
    public TextField loginNameField;
    @FXML
    public TextField passwordField;
    @FXML
    public TextField addressField;

    private GenericHibernate genericHibernate;
    private EntityManagerFactory entityManagerFactory;

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
    }

    public void createUser(ActionEvent actionEvent) {
        System.out.println("Creating user...");

            User user = new User(loginNameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText());
            genericHibernate.create(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Success");
            alert.setContentText("I have a great message for you!");


    }


}
