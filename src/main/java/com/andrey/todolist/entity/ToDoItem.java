package com.andrey.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "todo_list")
public class  ToDoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
//    @JsonIgnore
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "done")
    private boolean done;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    @JoinColumn(name = "username")
    private User user;

    public ToDoItem(String description, boolean done, User user) {
        this.description = description;
        this.done = done;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PreRemove
    public void deleteToDoItemFromUser(){
        user.getToDoLists().remove(this);
    }
}
