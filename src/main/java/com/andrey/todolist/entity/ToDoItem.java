package com.andrey.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "todo_list")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  ToDoItem extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "done")
    private boolean done;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @PreRemove
    public void deleteToDoItemFromUser(){
        user.getToDoLists().remove(this);
    }
}
