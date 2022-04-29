package com.andrey.todolist.service;

import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();
    public User findByName(String name);
    public List<ToDoItem> getToDoListByName(String name);
}
