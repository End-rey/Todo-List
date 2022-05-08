package com.andrey.todolist.service.impl;

import com.andrey.todolist.entity.RefreshToken;
import com.andrey.todolist.entity.Status;
import com.andrey.todolist.exceptions.TokenRefreshException;
import com.andrey.todolist.repository.RefreshTokenRepo;
import com.andrey.todolist.repository.UserRepo;
import com.andrey.todolist.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refresh.token.expired}")
    private Long refreshTokenDurationMs;

    private RefreshTokenRepo refreshTokenRepo;
    private UserRepo userRepo;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepo refreshTokenRepo, UserRepo userRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
        this.userRepo = userRepo;
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepo.findById(userId).get());
        Date now = new Date();
        refreshToken.setCreated(now);
        refreshToken.setUpdated(now);
        refreshToken.setStatus(Status.ACTIVE);
        refreshToken.setExpiryDate(new Date(now.getTime() + refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepo.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().before(new Date())){
            refreshTokenRepo.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token is expired");
        }
        return token;
    }

    @Override
    public void deleteByUserId(Long userId) {
        refreshTokenRepo.deleteByUser(userRepo.findById(userId).get());
    }
}
