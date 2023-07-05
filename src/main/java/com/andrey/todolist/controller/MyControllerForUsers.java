package com.andrey.todolist.controller;

import com.andrey.todolist.dto.UserRequestDto;
import com.andrey.todolist.dto.UserResponseDto;
import com.andrey.todolist.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
@SecurityRequirement(name = "todo-api")
public class MyControllerForUsers {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public Iterable<UserResponseDto> showAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/users/{username}")
    public UserResponseDto showUserByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @PutMapping("/users")
    public UserResponseDto updateUser(@RequestBody UserRequestDto userRequestDto){
        return userService.updateUser(userRequestDto);
    }

    @DeleteMapping("/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserByUsername(@PathVariable String username){
        userService.deleteUserByUsername(username);
    }

}
