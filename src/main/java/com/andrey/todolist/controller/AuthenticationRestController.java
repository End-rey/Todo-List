package com.andrey.todolist.controller;

import com.andrey.todolist.dto.AuthenticationRequestDto;
import com.andrey.todolist.dto.AuthenticationResponseDto;
import com.andrey.todolist.dto.TokenRefreshRequestDto;
import com.andrey.todolist.dto.UserRequestDto;
import com.andrey.todolist.dto.UserResponseDto;
import com.andrey.todolist.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data/auth/")
public class AuthenticationRestController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signin")
    public AuthenticationResponseDto authenticateUser(@RequestBody AuthenticationRequestDto requestDto){
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/refreshtoken")
    public AuthenticationResponseDto refreshToken(@RequestBody TokenRefreshRequestDto requestDto){
        return authenticationService.refreshToken(requestDto);
    }

    @PostMapping("/register")
    public UserResponseDto registerUser(@RequestBody UserRequestDto requestDto){
        return authenticationService.register(requestDto);
    }
}
