package org.example.criesandhope.hibernateControl;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.criesandhope.model.*;

import java.util.ArrayList;
import java.util.List;

public class CustomHibernate extends GenericHibernate {
    public CustomHibernate(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
    public User getUserByCredentials(String login, String psw) {
        User user = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);

            query.select(root).where(cb.and(cb.equal(root.get("login"), login),
                    cb.equal(root.get("password"), psw)));
            Query q = entityManager.createQuery(query);
            user = (User) q.getSingleResult();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Wrong credentials");
            alert.setContentText("Ooops, there was an error!");

            alert.showAndWait();

        }
        return user;
    }
    public List<FoodOrder> getFoodOrdersByRestaurantAndExcludingStatus(Restaurant restaurant, OrderStatus status) {
        List<FoodOrder> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<FoodOrder> query = cb.createQuery(FoodOrder.class);

            Root<FoodOrder> root = query.from(FoodOrder.class);

            // WHERE restaurant.id = X AND orderStatus = PREPARING
            query.select(root)
                    .where(
                            cb.and(
                                    cb.equal(root.get("restaurant").get("id"), restaurant.getId()),
                                    cb.notEqual(root.get("orderStatus"), status)
                            )
                    )
                    .orderBy(cb.desc(root.get("id"))); // newest first

            list = entityManager.createQuery(query).getResultList();

        } catch (Exception e) {
            standartDialogs.errorDialog("Could not load orders by restaurant & status: " + e.getMessage());
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return list;
    }
    public List<Cuisine> getCuisinesForOrder(int orderId) {
        List<Cuisine> result = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            FoodOrder order = entityManager.find(FoodOrder.class, orderId);

            if (order != null && order.getCuisineList() != null) {
                // priverstinė inicializacija, kol dar atidarytas EntityManager
                order.getCuisineList().size();
                result = new ArrayList<>(order.getCuisineList());
            }
        } catch (Exception e) {
            standartDialogs.errorDialog("Could not load cuisines for order: " + e.getMessage());
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return result;
    }
    public List<FoodOrder> getFoodOrdersExcludingStatus(OrderStatus excludedStatus) {
        List<FoodOrder> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<FoodOrder> query = cb.createQuery(FoodOrder.class);
            Root<FoodOrder> root = query.from(FoodOrder.class);

            // lyginame pagal ORDINAL — Hibernate automatiškai konvertuoja
            query.select(root)
                    .where(cb.notEqual(root.get("orderStatus"), excludedStatus));

            // rikiuoti: naujausi viršuje
            query.orderBy(cb.desc(root.get("id")));

            list = entityManager.createQuery(query).getResultList();

        } catch (Exception e) {
            standartDialogs.errorDialog("Could not get food orders excluding status: " + e.getMessage());
        }
        return list;
    }
    public List<Chat> getChatsForOrder(FoodOrder order) {
        List<Chat> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Chat> query = cb.createQuery(Chat.class);
            Root<Chat> root = query.from(Chat.class);

            query.select(root)
                    .where(cb.equal(root.get("foodOrder").get("id"), order.getId()))
                    .orderBy(cb.asc(root.get("createdAt")));

            list = entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            standartDialogs.errorDialog("Could not load chat messages: " + e.getMessage());
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return list;
    }
    public List<FoodOrder> getActiveOrdersForBasicUser(BasicUser user) {
        List<FoodOrder> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<FoodOrder> query = cb.createQuery(FoodOrder.class);
            Root<FoodOrder> root = query.from(FoodOrder.class);

            query.select(root)
                    .where(
                            cb.and(
                                    cb.equal(root.get("buyer").get("id"), user.getId()),
                                    cb.notEqual(root.get("orderStatus"), OrderStatus.COMPLETED)
                            )
                    )
                    .orderBy(cb.desc(root.get("id")));

            list = entityManager.createQuery(query).getResultList();

        } catch (Exception e) {
            standartDialogs.errorDialog("Could not load active user orders: " + e.getMessage());
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return list;
    }





}
