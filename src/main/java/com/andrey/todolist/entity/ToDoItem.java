package com.andrey.todolist.entity;

import javax.persistence.*;

@Entity
@Table(name = "todo_list")
public class ToDoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "done")
    private boolean done;

    public ToDoItem(int id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public ToDoItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
