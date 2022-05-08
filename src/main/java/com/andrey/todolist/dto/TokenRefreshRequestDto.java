package com.andrey.todolist.dto;

import lombok.Data;

@Data
public class TokenRefreshRequestDto {
    private String refreshToken;
}
