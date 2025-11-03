package org.example.criesandhope.fxController;

import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.example.criesandhope.HelloApplication;
import org.example.criesandhope.hibernateControl.CustomHibernate;
import org.example.criesandhope.hibernateControl.GenericHibernate;
import org.example.criesandhope.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainForm implements Initializable {
    public Tab userTab;
    public TableView<UserTableParameters> userTable;
    public TabPane tabsPane;
    public TableColumn<UserTableParameters, Integer> idColumn;
    public TableColumn<UserTableParameters, String> loginColumn;
    public TableColumn<UserTableParameters, String> passwordColumn;
    public TableColumn<UserTableParameters, String> nameColumn;
    public TableColumn<UserTableParameters, String> surnameColumn;
    public TableColumn<UserTableParameters, String> addressColumn;
    public TableColumn<UserTableParameters, String> userTypeColumn;
    public TableColumn<UserTableParameters, String> phoneNumberColumn;
    public TableColumn<UserTableParameters, String> actionColumn; //gali reiket keist kazkaip padryt delete mygtuka
    public TextField filterLogin;
    public TextField filterName;
    public TextField filterSurname;
    public TextField filterAddress;
    public TextField filterPhoneNum;


    private EntityManagerFactory entityManagerFactory;
    private CustomHibernate customHibernate;
    private User currentUser;
    private ObservableList<UserTableParameters> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userTable.setEditable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            user.setPassword(event.getNewValue());
            customHibernate.update(user);
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            user.setName(event.getNewValue());
            customHibernate.update(user);
        });

        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSurname(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            user.setSurname(event.getNewValue());
            customHibernate.update(user);
        });

        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
//        kaszkas ne to  !!!!!!!!!!
//        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        addressColumn.setOnEditCommit(event -> {
//            event.getTableView().getItems().get(event.getTablePosition().getRow()).setAddress(event.getNewValue());
//            BasicUser basicUser = customHibernate.getEntityById(BasicUser.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
//            basicUser.setAddress(event.getNewValue());
//            customHibernate.update(basicUser);
//        });

        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPhoneNum(event.getNewValue());
            User user = customHibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            user.setPhoneNumber(event.getNewValue());
            customHibernate.update(user);
        });


    }

    public void reloadTableData() {
        if (userTab.isSelected()) {
            userTable.getItems().clear();
            List<User> userList = customHibernate.getAllRecords(User.class);
            for(User u : userList){
                UserTableParameters userTableParameters = new UserTableParameters();
                userTableParameters.setId(u.getId());
                userTableParameters.setLogin(u.getLogin());
                userTableParameters.setPassword(u.getPassword());
                userTableParameters.setName(u.getName());
                userTableParameters.setSurname(u.getSurname());
                userTableParameters.setPhoneNum(u.getPhoneNumber());
                userTableParameters.setUserType(u.getClass().getSimpleName());
                if(u instanceof BasicUser) {
                    userTableParameters.setAddress(((BasicUser) u).getAddress());
                }
//                Baibaik debilas tu, Mantas is praeities
//                else if (u instanceof Restaurant){
//                    userTableParameters.setAddress(((Restaurant) u).getAddress());
//                }
//                else if (u instanceof Client){
//                    userTableParameters.setAddress(((Client) u).getAddress());
//                }
//                else if (u instanceof Driver){
//                    userTableParameters.setAddress(((Driver) u).getAddress());
//                }

                userTable.getItems().add(userTableParameters);


            }


        }

    }
    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.customHibernate = new CustomHibernate(entityManagerFactory);
        reloadTableData();
    }


    public void filterUsers(ActionEvent actionEvent) {
        List<User> filteredUsers = customHibernate.getByCredentials(filterLogin.getText(), filterName.getText(), filterSurname.getText(),  filterPhoneNum.getText());
        userTable.getItems().clear();
        for(User u : filteredUsers){
            UserTableParameters userTableParameters = new UserTableParameters();
            userTableParameters.setId(u.getId());
            userTableParameters.setLogin(u.getLogin());
            userTableParameters.setPassword(u.getPassword());
            userTableParameters.setName(u.getName());
            userTableParameters.setSurname(u.getSurname());
            userTableParameters.setPhoneNum(u.getPhoneNumber());
            userTableParameters.setUserType(u.getClass().getSimpleName());
            if(u instanceof BasicUser) {
                userTableParameters.setAddress(((BasicUser) u).getAddress());
            }
            userTable.getItems().add(userTableParameters);
        }


    }

    public void createUser()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-form.fxml"));
        Parent parent = fxmlLoader.load();

        UserForm userForm = fxmlLoader.getController();
        userForm.setData(entityManagerFactory);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }
}
