package com.andrey.todolist.service;

import com.andrey.todolist.dto.UserRequestDto;
import com.andrey.todolist.dto.UserResponseDto;
import com.andrey.todolist.entity.User;

import java.security.Principal;

public interface UserService {
    Iterable<UserResponseDto> findAll();
    UserResponseDto findByUsername(String name);
    UserResponseDto findById(Long id);
    UserResponseDto updateUser(UserRequestDto user);
    void deleteUserByUsername(String username);
    User findLoggedInUser(Principal principal);
}
