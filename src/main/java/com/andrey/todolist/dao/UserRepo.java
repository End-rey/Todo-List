package com.andrey.todolist.dao;

import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    public User getByUsername(String username);
    public void deleteByUsername(String name);
}
