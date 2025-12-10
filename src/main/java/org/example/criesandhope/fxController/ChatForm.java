package org.example.criesandhope.fxController;

import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.example.criesandhope.hibernateControl.CustomHibernate;
import org.example.criesandhope.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatForm {
    public ListView<FoodOrder> chatFoodOrdersList;
    public TextArea chatMessageField;
    public ListView<Chat> chatList;
    private EntityManagerFactory emf;
    private CustomHibernate customHibernate;
    private User currentUser;
    public void setData(EntityManagerFactory emf, User user) {
        this.emf = emf;
        this.currentUser = user;
        this.customHibernate = new CustomHibernate(emf);
        loadOrdersForUser();
    }
    private void loadOrdersForUser() {
        List<FoodOrder> orders;
        if (currentUser instanceof Restaurant r) {
            orders = customHibernate.getFoodOrdersByRestaurantAndExcludingStatus(r, OrderStatus.COMPLETED);
        }
        else if (currentUser instanceof Driver d) {
            // jei FoodOrder turi lauką Driver – filtruoji pagal jį
            orders = customHibernate.getFoodOrdersExcludingStatus(OrderStatus.COMPLETED);     // jei neturi – kol kas imk visus ne DELIVERED
        }
        else if (currentUser instanceof BasicUser bu) {
            orders = customHibernate.getActiveOrdersForBasicUser(bu);     // turi pasidaryt analogišką metodą kaip kitus
        } else {
            orders = customHibernate.getAllRecords(FoodOrder.class);
        }
        chatFoodOrdersList.getItems().setAll(orders);
    }
    private void loadMessagesForOrder(FoodOrder order) {
        if (order == null) {
            chatList.getItems().clear();
            return;
        }
        chatList.getItems().setAll(
                customHibernate.getChatsForOrder(order)
        );
    }



    public void LoadOrderChat(MouseEvent mouseEvent) {
        FoodOrder selected = chatFoodOrdersList.getSelectionModel().getSelectedItem();
        chatList.getItems().setAll(
                customHibernate.getChatsForOrder(selected));

    }
    public void CreateChat(ActionEvent actionEvent) {
        FoodOrder order = chatFoodOrdersList.getSelectionModel().getSelectedItem();
        String text = chatMessageField.getText();

        if (order == null || text == null || text.isBlank()) {
            return;
        }

        Chat msg = new Chat();
        msg.setChatText(text);
        msg.setFoodOrder(order);                    // nustatom ryšį iš Chat pusės
        msg.setCreatedAt(LocalDateTime.now());
        msg.setLastMessageAt(LocalDateTime.now());
        msg.setDateCreated(LocalDate.now());
        msg.setActive(true);
        msg.setName(currentUser.getName());

        if (currentUser instanceof Restaurant r) {
            msg.setRestaurant(r);
        } else if (currentUser instanceof Driver d) {
            msg.setDriver(d);

        } else if (currentUser instanceof BasicUser bu) {
            msg.setCustomer(bu);
        }

        // 🔴 svarbiausia dalis: pridedam Chat į FoodOrder.chats sąrašą
        if (order.getChats() == null) {
            order.setChats(new ArrayList<>());
        }
        order.getChats().add(msg);

        // kadangi FoodOrder turi cascade = ALL ant chats, pakanka update(order)
        customHibernate.update(order);
        // (nereikia atskiro create(msg))

        chatMessageField.clear();
        loadMessagesForOrder(order);
    }


}
