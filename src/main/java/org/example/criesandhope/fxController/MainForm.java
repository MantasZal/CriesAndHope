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
import java.util.Objects;
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
    public TableColumn<UserTableParameters, String> licenceColumn;
    public TableColumn<UserTableParameters, String> bDateColumn; //del stringo gali reiket keist
    public TableColumn<UserTableParameters, String> cehicleTypeColumn;
    public TextField deleteUserIDField;
    public Tab reviewTab;
    public Tab ChatTab;
    public ListView<Chat> chatListView;
    public TextField chatTextField;
    public TextField chatUserNameField;
    public TextField chatIdField;
    public Tab foodManagment;
    public ListView<Cuisine> cuisineListView;
    public ComboBox<Restaurant> restaurantComboBox;
    public TextField cuisineNameField;
    public TextArea cuisineIngridentsArea;
    public CheckBox veganCheckBoc;
    public CheckBox SpicyCheckBox;
    public TextField cuisinePriceField;
    public TextField cuisineNameFilterField;
    public TextField cuisinePriceFilterField;
    private ObservableList<Restaurant> restaurants = FXCollections.observableArrayList();


    private EntityManagerFactory entityManagerFactory;
    private CustomHibernate customHibernate= new CustomHibernate(entityManagerFactory);
    private User currentUser;
    private ObservableList<UserTableParameters> data = FXCollections.observableArrayList();
    private ObservableList<Chat> chatMessages = FXCollections.observableArrayList();

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
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(event -> {
            if(!Objects.equals(event.getTableView().getItems().get(event.getTablePosition().getRow()).getUserType(), "User")) {
                //patikrint ar basic useris
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setAddress(event.getNewValue());
                BasicUser basicUser = customHibernate.getEntityById(BasicUser.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                basicUser.setAddress(event.getNewValue());
                customHibernate.update(basicUser);
            }
        });

            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
            phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            phoneNumberColumn.setOnEditCommit(event -> {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setPhoneNum(event.getNewValue());
                User user = customHibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                user.setPhoneNumber(event.getNewValue());
                customHibernate.update(user);
            });
            licenceColumn.setCellValueFactory(new PropertyValueFactory<>("licence"));
            bDateColumn.setCellValueFactory(new PropertyValueFactory<>("bDate"));
            cehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));

        chatListView.getItems().addAll(customHibernate.getAllRecords(Chat.class));







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
                if (u instanceof Driver) {
                    userTableParameters.setName(u.getName());
                    userTableParameters.setLicence(((Driver) u).getLicence());
                    userTableParameters.setbDate(String.valueOf(((Driver) u).getBDate()));
                    userTableParameters.setVehicleType(String.valueOf(((Driver) u).getVehicleType()));

                }
                userTable.getItems().add(userTableParameters);
            }
        }
        if (ChatTab.isSelected()) {
            chatListView.getItems().clear();
            chatListView.getItems().addAll(customHibernate.getAllRecords(Chat.class));
            restaurants.setAll(customHibernate.getAllRecords(Restaurant.class));
            restaurantComboBox.setItems(restaurants);

        }
        if(foodManagment.isSelected()){
            restaurants.setAll(customHibernate.getAllRecords(Restaurant.class));
            restaurantComboBox.setItems(restaurants);
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



    public void deletUser(ActionEvent actionEvent) {
        customHibernate.delete(User.class, deleteUserIDField.getText());
        reloadTableData();
    }

    public void createChat(ActionEvent actionEvent) {

        Chat chat = new Chat(currentUser.getName(), chatTextField.getText());
        customHibernate.create(chat);
        chatListView.getItems().add(chat);
    }

    public void filterChat(ActionEvent actionEvent) {
        List<Chat> filteredChats = customHibernate.getChatByCredentials(chatUserNameField.getText());
        chatListView.getItems().clear();
        chatListView.getItems().addAll(filteredChats);
    }

    public void deleteChat(ActionEvent actionEvent) {
        customHibernate.delete(Chat.class, Integer.parseInt(chatIdField.getText()));
        reloadTableData();

    }

    public void loadCuisineList(ActionEvent actionEvent) {
        cuisineListView.getItems().clear();
        cuisineListView.getItems().addAll(customHibernate.getCuisinesByRestaurant(restaurantComboBox.getValue()));
    }

    public void createNewCuisine(ActionEvent actionEvent) {
        Cuisine cuisine = new Cuisine(cuisineNameField.getText(), cuisineIngridentsArea.getText(),Double.parseDouble(cuisinePriceField.getText()), veganCheckBoc.isSelected(), SpicyCheckBox.isSelected(), restaurantComboBox.getValue());
        customHibernate.create(cuisine);
        cuisineListView.getItems().add(cuisine);
    }

    public void filterCuisine(ActionEvent actionEvent) {
    }
}
