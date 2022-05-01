package com.andrey.todolist.service;

import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;

import java.security.Principal;

public interface UserService {
    public Iterable<User> findAll();
    public User findByName(String name);
    public Iterable<ToDoItem> getAllToDoItems(Principal principal);
    public User saveUser(User user);
    public void deleteUser(User user);
    public void deleteCurrentlyLoggedInUser(Principal principal);
    public User findLoggedInUser(Principal principal);
}
