package org.example.criesandhope.fxController;

import com.mysql.cj.xdevapi.Client;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.criesandhope.hibernateControl.GenericHibernate;
import org.example.criesandhope.model.*;

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
    public TextField licenceField;
    public DatePicker licenceDatePIcker;
    public ComboBox<VehicleType> vechicleType;

    private GenericHibernate genericHibernate;
    private EntityManagerFactory entityManagerFactory;

    public void disableFields(ActionEvent actionEvent) {
        if (userRadio.isSelected()) {
            addressField.setDisable(true);
            licenceField.setDisable(true);
            licenceDatePIcker.setDisable(true);
            vechicleType.setDisable(true);

        } else if (restaurantRadio.isSelected()) {
            restaurantRadio.setSelected(true);
            addressField.setDisable(false);
            licenceField.setDisable(true);
            licenceDatePIcker.setDisable(true);
            vechicleType.setDisable(true);
        } else if (clientRadio.isSelected()) {
            clientRadio.setSelected(true);
            addressField.setDisable(false);
            licenceField.setDisable(true);
            licenceDatePIcker.setDisable(true);
            vechicleType.setDisable(true);
        } else if (driverRadio.isSelected()) {
            driverRadio.setSelected(true);
            addressField.setDisable(false);
        } else {
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vechicleType.getItems().addAll(VehicleType.values());

    }
    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
    }

    public void createUser(ActionEvent actionEvent) {
        if(userRadio.isSelected()){
            User user = new User(loginNameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText());
            genericHibernate.create(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Success");
            alert.setContentText("I have a great message for you!");
        }
        else if (driverRadio.isSelected()){
            Driver driver= new Driver(loginNameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText(), addressField.getText(), licenceField.getText(), licenceDatePIcker.getValue(), vechicleType.getValue());
            genericHibernate.create(driver);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Success");
            alert.setContentText("I have a great message for you!");
        } else if (restaurantRadio.isSelected()) {
            Restaurant restaurant = new Restaurant(loginNameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText(), addressField.getText());
            genericHibernate.create(restaurant);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Success");
            alert.setContentText("I have a great message for you!");
        } else if (clientRadio.isSelected()) {
            BasicUser basicUser = new BasicUser(loginNameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumberField.getText(), addressField.getText());
            genericHibernate.create(basicUser);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Success");
            alert.setContentText("I have a great message for you!");

        }


    }


}
