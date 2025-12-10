package org.example.criesandhope.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//kodel org o ne com

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BasicUser extends User {

    protected String address;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Chat>  chats;
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<FoodOrder> myOrders;
    @OneToMany(mappedBy = "commentOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Review> myReviews;
    @OneToMany(mappedBy = "feedbackUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Review> feedback;

//    public BasicUser(String login, String password, String name, String surname, String phoneNumber, String address) {
//        super(login, password, name, surname, phoneNumber);
//        this.address = address;
//        this.myReviews = new ArrayList<>();
//        this.feedback = new ArrayList<>();
//        this.myOrders = new ArrayList<>();
//    }

    public BasicUser(String login, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, LocalDateTime dateUpdated, String address) {
        super(login, password, name, surname, phoneNumber, dateCreated, dateUpdated);
        this.address = address;
    }

    @Override
    public String toString() {
        return name ;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FoodOrder> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(List<FoodOrder> myOrders) {
        this.myOrders = myOrders;
    }

    public List<Review> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Review> feedback) {
        this.feedback = feedback;
    }

    public List<Review> getMyReviews() {
        return myReviews;
    }

    public void setMyReviews(List<Review> myReviews) {
        this.myReviews = myReviews;
    }
}
