package com.andrey.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="users")
@Data
public class User extends BaseEntity {

//    @Id
    @Column(name="username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

//    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    @JoinColumn(name="id")
    private List<ToDoItem> toDoLists;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    public void addToDoItem(ToDoItem toDoItem) {
        if(toDoLists == null){
            toDoLists = new ArrayList<>();
        }
        toDoLists.add(toDoItem);
        toDoItem.setUser(this);
    }

}
