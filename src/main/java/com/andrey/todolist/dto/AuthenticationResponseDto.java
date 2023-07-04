package com.andrey.todolist.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private String username;
    private String accessToken;
    private String refreshToken;

    public AuthenticationResponseDto(String username, String accessToken, String refreshToken) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
