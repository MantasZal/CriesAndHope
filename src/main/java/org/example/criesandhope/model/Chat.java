package org.example.criesandhope.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String chatText;
    private LocalDate dateCreated;
    @OneToOne(mappedBy = "chat", cascade = CascadeType.ALL)
    private FoodOrder foodOrder;

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chatText='" + chatText + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }

    public Chat(String name, String chatText) {
        this.name = name;
        this.chatText = chatText;
    }
}
