package com.andrey.todolist.service;

import com.andrey.todolist.entity.RefreshToken;

public interface RefreshTokenService {
    public RefreshToken findByToken(String token);
    public RefreshToken createRefreshToken(Long userId);
    public RefreshToken verifyExpiration(RefreshToken token);
    public void deleteByUserId(Long userId);
    public RefreshToken findByUsername(String username);
}
