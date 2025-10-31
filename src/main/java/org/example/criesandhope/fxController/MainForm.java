package org.example.criesandhope.fxController;

import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.example.criesandhope.hibernateControl.CustomHibernate;
import org.example.criesandhope.hibernateControl.GenericHibernate;
import org.example.criesandhope.model.*;

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
    public TableColumn<UserTableParameters, String>phoneNumberColumn;
    public TableColumn<UserTableParameters, String> actionColumn; //gali reiket keist kazkaip padryt delete mygtuka


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
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));


    }

    public void reloadTableData() {
        if (userTab.isSelected()) {
            data.clear();
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
//                Baibaik debilas tu Mantas is praeities
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



}
