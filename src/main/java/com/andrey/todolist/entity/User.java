package com.andrey.todolist.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany
    @JoinColumn(name="username")
    private List<ToDoItem> toDoLists;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ToDoItem> getToDoLists() {
        return toDoLists;
    }

    public void setToDoLists(List<ToDoItem> toDoLists) {
        this.toDoLists = toDoLists;
    }
}
