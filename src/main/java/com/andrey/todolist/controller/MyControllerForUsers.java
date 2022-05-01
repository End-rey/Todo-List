package com.andrey.todolist.controller;

import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyControllerForUsers {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Iterable<User> showAllUsers(){
        Iterable<User> users = userService.findAll();

        return users;
    }

    @GetMapping("/users/{name}")
    public User showUserByName(@PathVariable String name){
        return userService.findByName(name);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @DeleteMapping("/users/{name}")
    public String deleteUserByName(@PathVariable String name){
        User user = userService.findByName(name);
        if(user != null) {
            userService.deleteUser(user);
            return "User with name " + name + " was deleted";
        } else
            return "No such user";
    }

}
