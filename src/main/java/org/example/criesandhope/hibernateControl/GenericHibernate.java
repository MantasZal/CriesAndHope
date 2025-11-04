package org.example.criesandhope.hibernateControl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.scene.control.Alert;
import org.example.criesandhope.model.Chat;
import org.example.criesandhope.model.Restaurant;
import org.example.criesandhope.model.User;
import org.example.criesandhope.utils.StandartDialogs;

import java.util.ArrayList;
import java.util.List;

public class GenericHibernate {
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected StandartDialogs standartDialogs= new StandartDialogs();

    public GenericHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public <T> void create(T entity) {
        try {
            //kaip padaryt jog pridetu kada sukurta
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity); //INSERT
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            standartDialogs.errorDialog("Coulnd not create record: " + e.getMessage());
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public <T> void update(T entity) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(entity); //UPDATE
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            standartDialogs.errorDialog("Coulnd not update record: " + e.getMessage());
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public <T> T getEntityById(Class<T> entityClass, int id) {
        T entity = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity = entityManager.find(entityClass, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            standartDialogs.errorDialog("Coulnd not find record by id: " + id);
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return entity;
    }

    //Gali buti blogai su detached entity
    public <T> void delete(Class<T> entityClass, int id) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            T entity = entityManager.find(entityClass, id);
            entityManager.remove(entity); //DELETE
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            standartDialogs.errorDialog("Coulnd not find record by id: " + id);

        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public <T> List<T> getAllRecords(Class<T> entityClass) {
        List<T> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(entityClass));
            Query q = entityManager.createQuery(query);
            list = q.getResultList();
        } catch (Exception e) {
            standartDialogs.errorDialog("Coulnd not get all records: " + e.getMessage());
        }
        return list;
    }
    public List<User> getByCredentials(String login, String name, String surname, String phoneNumber) {
        List<User> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.and(cb.like(root.get("login"), "%" + login + "%"), cb.like(root.get("name"), "%" + name + "%"),
                    cb.like(root.get("surname"), "%" + surname + "%"), cb.like(root.get("phoneNumber"), "%" + phoneNumber + "%")));
            Query q = entityManager.createQuery(query);
            list = q.getResultList();
        } catch (Exception e) {
            standartDialogs.errorDialog("Could not find logs by credentials" + e.getMessage());
        }
        return list;
    }
    public <T> void delete(Class<T> entityClass, Object id) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            // Surandame objektą pagal ID
            T entity = entityManager.find(entityClass, id);
            if (entity != null) {
                entityManager.remove(entity); // DELETE
                entityManager.getTransaction().commit();
                System.out.println("Įrašas sėkmingai ištrintas!");
            } else {
                System.out.println("Įrašas su ID " + id + " nerastas!");
            }

        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }


    public List<Chat> getChatByCredentials(String text) {
        List<Chat> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Chat> query = cb.createQuery(Chat.class);
            Root<Chat> root = query.from(Chat.class);
            query.select(root).where(cb.and(cb.like(root.get("name"), "%" + text + "%")));
            Query q = entityManager.createQuery(query);
            list = q.getResultList();
        } catch (Exception e) {
            standartDialogs.errorDialog("Could not find chats by credentials" + e.getMessage());
        }
        return list;
    }
}
