package org.example.criesandhope.fxController;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.criesandhope.HelloApplication;

import java.io.IOException;

public class LoginForm {
    public TextField loginField;
    public PasswordField passwordField;

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CriesAndHope_DB");

    public void validateAndLoad() {
    }

    public void registerNewUser() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-form.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            Stage stage = new Stage();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
    }
}
