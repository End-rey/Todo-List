package com.andrey.todolist.repository;

import com.andrey.todolist.entity.RefreshToken;
import com.andrey.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
    public RefreshToken findByUser(User user);
    public RefreshToken findByToken(String token);
    public void deleteByUser(User user);
}
