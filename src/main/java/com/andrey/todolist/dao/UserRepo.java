package com.andrey.todolist.dao;

import com.andrey.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface UserRepo extends JpaRepository<User, String> {
    public User getByUsername(String username);
}
