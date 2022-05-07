package com.andrey.todolist.controller;

import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyControllerForUsers {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public Iterable<User> showAllUsers(){
        Iterable<User> users = userService.findAll();

        return users;
    }

    @GetMapping("/users/{username}")
    public User showUserByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @PostMapping("/users")
    public User registerUser(@RequestBody User user){
        return userService.register(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @DeleteMapping("/users/{username}")
    public String deleteUserByUsername(@PathVariable String username){
        User user = userService.findByUsername(username);
        if(user != null) {
            userService.deleteUser(user);
            return "User with username " + username + " was deleted";
        } else
            return "No such user";
    }

}
