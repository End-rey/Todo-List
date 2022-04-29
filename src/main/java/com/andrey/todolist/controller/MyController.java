package com.andrey.todolist.controller;

import com.andrey.todolist.dao.UserRepo;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> showAllUsers(){
        List<User> users = userService.findAll();

        return users;
    }
}
