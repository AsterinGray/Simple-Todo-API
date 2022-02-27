package com.spring.Simple.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name="description")
    private String description;
    @Column(name="date")
    private String date;


    public Todo(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Todo() {

    }
}
