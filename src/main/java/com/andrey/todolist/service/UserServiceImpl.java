package com.andrey.todolist.service;

import com.andrey.todolist.dao.UserRepo;
import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findByName(String name) {
        return userRepo.getByUsername(name);
    }

    @Override
    public List<ToDoItem> getToDoListByName(String name) {
        User user = userRepo.getByUsername(name);
        List<ToDoItem> toDoList = user.getToDoLists();
        return toDoList;
    }
}
