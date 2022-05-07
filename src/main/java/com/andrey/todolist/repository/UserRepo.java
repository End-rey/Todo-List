package com.andrey.todolist.repository;

import com.andrey.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    public void deleteByUsername(String name);

}
