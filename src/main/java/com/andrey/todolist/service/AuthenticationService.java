package com.andrey.todolist.service;

import com.andrey.todolist.dto.AuthenticationRequestDto;
import com.andrey.todolist.dto.AuthenticationResponseDto;
import com.andrey.todolist.dto.TokenRefreshRequestDto;
import com.andrey.todolist.dto.UserRequestDto;
import com.andrey.todolist.dto.UserResponseDto;

public interface AuthenticationService {
    AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto);
    AuthenticationResponseDto refreshToken(TokenRefreshRequestDto requestDto);
    UserResponseDto register(UserRequestDto requestDto);
}
