package org.example.criesandhope.fxController;

import com.mysql.cj.xdevapi.Client;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.criesandhope.HelloApplication;
import org.example.criesandhope.hibernateControl.CustomHibernate;
import org.example.criesandhope.hibernateControl.GenericHibernate;
import org.example.criesandhope.model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

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
    public TableColumn<UserTableParameters, String> licenceColumn;
    public TableColumn<UserTableParameters, String> bDateColumn; //del stringo gali reiket keist
    public TableColumn<UserTableParameters, String> cehicleTypeColumn;
    public TextField filterLogin;
    public TextField filterName;
    public TextField filterSurname;
    public TextField filterAddress;
    public TextField filterPhoneNum;

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
    public TextField cuisineIdField;
    public ListView<Cuisine> orderCuisineListView;
    public Tab foodOderTab;
    public ListView<FoodOrder> foodOrderListView;
    public ComboBox<BasicUser> clientComboBox;
    public TextField orderNameField;
    public TextField orderPriceField;
    public ComboBox<Restaurant> orderRestaurantComboBox;
    public TextField orderNameFilterField;
    public TextField orderPriceFilterField;
    public Tab orderTab;
    public ComboBox<Restaurant> orderRestaurantsBox;
    public ListView<Cuisine> orderMeniuList;
    public ListView<Cuisine> orderList;
    public Button orderMakeFoodOrder;
    public Text orderCost;
    public ListView<FoodOrder> ordersUsersFoodOrders;
    public Text foodManagmentRestName;
    public Button foodOrderCreateButton;
    public Button foodOrderUpdateButton;
    public Button foodOrderDeleteButton;
    public ComboBox<OrderStatus> foodOrderOrderStatus;
    public Tab accountTab;
    public TextField accountNameField;
    public TextField accountSurnameField;
    public TextField accountLoginField;
    public TextField accountPasswordField;
    public TextField accountAdressField;
    public TextField accountNumbField;
    public TextField accountLicenseField;
    public DatePicker accountBDateField;
    public ComboBox<VehicleType> accountVechileTypeComboBox;
    private ObservableList<Restaurant> restaurants = FXCollections.observableArrayList();
    private ObservableList<BasicUser> clients = FXCollections.observableArrayList();


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
                user.setDateUpdated(LocalDateTime.now());
                customHibernate.update(user);
            });

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            nameColumn.setOnEditCommit(event -> {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
                User user = customHibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                user.setName(event.getNewValue());
                user.setDateUpdated(LocalDateTime.now());
                customHibernate.update(user);
            });

            surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
            surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            surnameColumn.setOnEditCommit(event -> {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setSurname(event.getNewValue());
                User user = customHibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                user.setSurname(event.getNewValue());
                user.setDateUpdated(LocalDateTime.now());
                customHibernate.update(user);
            });

            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(event -> {
            if(!Objects.equals(event.getTableView().getItems().get(event.getTablePosition().getRow()).getUserType(), "User")) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setAddress(event.getNewValue());
                BasicUser basicUser = customHibernate.getEntityById(BasicUser.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                basicUser.setAddress(event.getNewValue());
                basicUser.setDateUpdated(LocalDateTime.now());
                customHibernate.update(basicUser);
            }
        });

            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
            phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            phoneNumberColumn.setOnEditCommit(event -> {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setPhoneNum(event.getNewValue());
                User user = customHibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                user.setPhoneNumber(event.getNewValue());
                user.setDateUpdated(LocalDateTime.now());
                customHibernate.update(user);
            });
            licenceColumn.setCellValueFactory(new PropertyValueFactory<>("licence"));
            bDateColumn.setCellValueFactory(new PropertyValueFactory<>("bDate"));
            cehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));


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
            if (currentUser instanceof Restaurant) {
                foodManagmentRestName.setText("Restaurant: " + currentUser.getName());
                restaurantComboBox.setValue((Restaurant) currentUser);
                restaurantComboBox.setDisable(true);
            } else {
                foodManagmentRestName.setText("Restaurant: All");
                restaurantComboBox.setDisable(false);
            }
            restaurants.setAll(customHibernate.getAllRecords(Restaurant.class));
            restaurantComboBox.setItems(restaurants);
        }
        if(foodOderTab.isSelected()){
            //clearAllFields(); //Patikrink kas cia vyksta
            clients.setAll(customHibernate.getAllRecords(BasicUser.class));
            clientComboBox.setItems(clients);

            restaurants.setAll(customHibernate.getAllRecords(Restaurant.class));
            orderRestaurantComboBox.setItems(restaurants);

            foodOrderOrderStatus.getItems().setAll(OrderStatus.values());

            if (currentUser instanceof Restaurant restaurantUser) {
                foodOrderListView.getItems().setAll(
                        customHibernate.getFoodOrdersByRestaurantAndStatus(restaurantUser, OrderStatus.PREPARING).stream()
                                .sorted(Comparator.comparingInt(FoodOrder::getId).reversed())
                                .toList()
                );
                clientComboBox.setDisable(true);
                orderRestaurantComboBox.setDisable(true);
                orderNameField.setDisable(true);
                orderPriceField.setDisable(true);
                foodOrderCreateButton.setDisable(true);
                foodOrderDeleteButton.setDisable(true);



            }
            else if (currentUser instanceof Driver) {
                foodOrderListView.getItems().setAll(
                        customHibernate.getFoodOrdersExcludingStatus(OrderStatus.COMPLETED)
                );

                clientComboBox.setDisable(true);
                orderRestaurantComboBox.setDisable(true);
                orderNameField.setDisable(true);
                orderPriceField.setDisable(true);
                foodOrderCreateButton.setDisable(true);
                foodOrderDeleteButton.setDisable(true);
            }
            else {
                foodOrderListView.getItems().setAll(
                        customHibernate.getAllRecords(FoodOrder.class)
                );
            }
        }
        if(orderTab.isSelected()){
            ordersUsersFoodOrders.getItems().clear();
            ordersUsersFoodOrders.getItems().setAll(
                    customHibernate.getAllRecords(FoodOrder.class)
                            .stream()
                            .sorted(Comparator.comparingInt(FoodOrder::getId).reversed())
                            .toList()
            );
            orderMeniuList.getItems().clear();
            restaurants.setAll(customHibernate.getAllRecords(Restaurant.class));
            orderRestaurantsBox.setItems(restaurants);

        }
        if(accountTab.isSelected()){
            accountLoginField.setText(currentUser.getLogin());
            accountPasswordField.setText(currentUser.getPassword());
            accountNameField.setText(currentUser.getName());
            accountSurnameField.setText(currentUser.getSurname());
            accountNumbField.setText(currentUser.getPhoneNumber());
            accountAdressField.setDisable(true);
            accountLicenseField.setDisable(true);
            accountBDateField.setDisable(true);
            accountVechileTypeComboBox.setDisable(true);
            if(currentUser instanceof BasicUser){
                accountAdressField.setDisable(false);
                accountAdressField.setText(((BasicUser) currentUser).getAddress());
            }
            if(currentUser instanceof Driver){
                accountAdressField.setDisable(false);
                accountLicenseField.setDisable(false);
                accountBDateField.setDisable(false);
                accountVechileTypeComboBox.setDisable(false);
                accountAdressField.setText(((Driver) currentUser).getAddress());
                accountLicenseField.setText(((Driver) currentUser).getLicence());
                accountBDateField.setValue(((Driver) currentUser).getBDate());
                accountVechileTypeComboBox.getItems().setAll(VehicleType.values());
                accountVechileTypeComboBox.setValue(((Driver) currentUser).getVehicleType());
            }
        }

    }
    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.customHibernate = new CustomHibernate(entityManagerFactory);
        tabsPane.getTabs().clear();
        tabsPane.getTabs().addAll(accountTab);

        if (user instanceof Driver) {
            tabsPane.getTabs().addAll(foodOderTab);
            tabsPane.getSelectionModel().select(foodOderTab);


        }
        else if (user instanceof Restaurant) {
            tabsPane.getTabs().addAll(foodManagment, foodOderTab);
            tabsPane.getSelectionModel().select(foodManagment);
        }

        else if (user instanceof BasicUser) {
            tabsPane.getTabs().add(orderTab);
            tabsPane.getSelectionModel().select(orderTab);
        }
        else if (user instanceof User) {
            tabsPane.getTabs().addAll(userTab, foodOderTab, ChatTab, foodManagment);
            tabsPane.getSelectionModel().select(userTab);

        }
        reloadTableData();
    }


    //<editor-fold desc="User Functionality"> //ctr alt t
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
    //</editor-fold>

    //<editor-fold desc="Chat Tab Functionality"> //ctr alt t
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
    //</editor-fold>

    //<editor-fold desc="Cuisine Tab Functionality"> //ctr alt t
    public void loadCuisineList(ActionEvent actionEvent) {
        List <Cuisine> cuisines = customHibernate.getCuisinesByRestaurant(restaurantComboBox.getValue());
        cuisineListView.getItems().clear();
        cuisineListView.getItems().addAll(cuisines);
    }

    public void createNewCuisine(ActionEvent actionEvent) {
        Cuisine cuisine = new Cuisine(cuisineNameField.getText(), cuisineIngridentsArea.getText(),Double.parseDouble(cuisinePriceField.getText()), veganCheckBoc.isSelected(), SpicyCheckBox.isSelected(), restaurantComboBox.getValue());
        customHibernate.create(cuisine);
        cuisineListView.getItems().add(cuisine);
    }

    public void filterCuisine(ActionEvent actionEvent) {
        List<Cuisine> filteredCuisines = customHibernate.getCuisineByCredentials(cuisineNameFilterField.getText(), cuisinePriceFilterField.getText(), String.valueOf(restaurantComboBox.getValue()));
        cuisineListView.getItems().clear();
        cuisineListView.getItems().addAll(filteredCuisines);
    }

    public void deleteCuisine(ActionEvent actionEvent) {
        customHibernate.delete(Cuisine.class, Integer.parseInt(cuisineIdField.getText()));
        reloadTableData();
    }
    //</editor-fold>


    //<editor-fold desc="Order Tab Functionality"> //ctr alt t
    public void deleteOrder(ActionEvent actionEvent) {
        FoodOrder selectedOrder = foodOrderListView.getSelectionModel().getSelectedItem();
        customHibernate.delete(FoodOrder.class, selectedOrder.getId());
        reloadTableData();
    }

    public void createOrder(ActionEvent actionEvent) {
        FoodOrder foodOrder = new FoodOrder(orderNameField.getText(), Double.parseDouble(orderPriceField.getText()),  clientComboBox.getValue() , orderRestaurantComboBox.getValue());
        customHibernate.create(foodOrder);
        foodOrderListView.getItems().clear();
        foodOrderListView.getItems().addAll(foodOrder);
        reloadTableData();
    }

    public void updateOrder(ActionEvent actionEvent) {
        FoodOrder foodOrder = foodOrderListView.getSelectionModel().getSelectedItem();
        foodOrder.setRestaurant(orderRestaurantComboBox.getSelectionModel().getSelectedItem());
        foodOrder.setName(orderNameField.getText());
        foodOrder.setPrice(Double.valueOf(orderPriceField.getText()));
        foodOrder.setBuyer(clientComboBox.getSelectionModel().getSelectedItem());
        foodOrder.setOrderStatus(foodOrderOrderStatus.getSelectionModel().getSelectedItem());
        customHibernate.update(foodOrder);
        reloadTableData();

    }
    public void loadOrderInfo(MouseEvent mouseEvent) {
        @SuppressWarnings("unchecked")
        ListView<FoodOrder> source =
                (ListView<FoodOrder>) mouseEvent.getSource();

        FoodOrder selectedOrder = source.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            orderNameField.setText(selectedOrder.getName());
            orderPriceField.setText(String.valueOf(selectedOrder.getPrice()));
            clientComboBox.setValue(selectedOrder.getBuyer());
            orderRestaurantComboBox.setValue(selectedOrder.getRestaurant());
            foodOrderOrderStatus.setValue(selectedOrder.getOrderStatus());

            orderCuisineListView.getItems().clear();
            orderCuisineListView.getItems().setAll(
                    customHibernate.getCuisinesForOrder(selectedOrder.getId())
            );
        }
    }
    public void filterOrder(ActionEvent actionEvent) {
        List<FoodOrder> filteredOrders = customHibernate.getFoodOrderByCredentials(orderNameFilterField.getText(), orderPriceFilterField.getText());
        foodOrderListView.getItems().clear();
        foodOrderListView.getItems().addAll(filteredOrders);

    }
    //</editor-fold>

    //<editor-fold desc="User order Tab Functionality"> //ctr alt t
    public void OrderMakeFoodOrder(ActionEvent actionEvent) {
        if (orderRestaurantsBox.getValue() == null || orderList.getItems().isEmpty()) {
            // čia gali išmesti alert'ą, kad reikia pasirinkti restoraną ir patiekalus
            return;
        }

        Restaurant restaurant = orderRestaurantsBox.getValue();

        BasicUser buyer = (BasicUser) currentUser;   // jei currentUser tikrai BasicUser

        List<Cuisine> chosenDishes = new ArrayList<>(orderList.getItems());
        double total = chosenDishes.stream()
                .mapToDouble(Cuisine::getPrice)
                .sum();

        FoodOrder order = new FoodOrder();
        order.setName("Order_" + System.currentTimeMillis());
        order.setPrice(total);
        order.setBuyer(buyer);
        order.setRestaurant(restaurant);
        order.setCuisineList(chosenDishes);
        order.setOrderStatus(OrderStatus.PREPARING);  // arba koks ten tavo pradinis statusas

        customHibernate.create(order);

        // išvalom UI
        orderList.getItems().clear();
        recalcOrderCost();
        reloadTableData();
    }

    public void LoadMeniu(ActionEvent actionEvent) {
        List<Cuisine> foodOrders = customHibernate.getCuisinesByRestaurant(orderRestaurantsBox.getValue());
        orderMeniuList.getItems().clear();
        orderMeniuList.getItems().addAll(foodOrders);
    }

    public void addDishToOrder(MouseEvent mouseEvent) {
        Cuisine selected = orderMeniuList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            orderList.getItems().add(selected);
            recalcOrderCost();
        }
    }
    private void recalcOrderCost() {
        double total = orderList.getItems().stream()
                .mapToDouble(Cuisine::getPrice)
                .sum();
        orderCost.setText(String.valueOf(total));
    }


    //</editor-fold>
    public void SaveUserAccount(ActionEvent actionEvent) {
        currentUser.setLogin(accountLoginField.getText());
        currentUser.setPassword(accountPasswordField.getText());
        currentUser.setName(accountNameField.getText());
        currentUser.setSurname(accountSurnameField.getText());
        currentUser.setPhoneNumber(accountNumbField.getText());
        currentUser.setDateUpdated(LocalDateTime.now());
        if(currentUser instanceof BasicUser){
            ((BasicUser) currentUser).setAddress(accountAdressField.getText());
        }
        if(currentUser instanceof Driver){
            ((Driver) currentUser).setAddress(accountAdressField.getText());
            ((Driver) currentUser).setLicence(accountLicenseField.getText());
            ((Driver) currentUser).setBDate(accountBDateField.getValue());
            ((Driver) currentUser).setVehicleType(accountVechileTypeComboBox.getValue());
        }
        customHibernate.update(currentUser);
        reloadTableData();
    }




}
