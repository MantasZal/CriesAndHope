package org.example.criesandhope.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cuisine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    protected String name;
    protected String ingredients;
    protected Double price;
    protected boolean spicy = false;
    protected boolean vegan = false;
    @ManyToMany(mappedBy = "cuisineList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodOrder> orderList;
    @ManyToOne
    private Restaurant restaurant;


    @Override
    public String toString() {
        return  id +": '" + name + '\'' +
                ", " + ingredients +
                ", " + price +
                ", spicy=" + spicy +
                ", vegan=" + vegan ;
    }

    public Cuisine(String name, String ingredients, Double price, boolean spicy, boolean vegan, Restaurant restaurant) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.spicy = spicy;
        this.vegan = vegan;
        this.restaurant = restaurant;
    }
}
