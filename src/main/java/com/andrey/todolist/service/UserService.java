package com.andrey.todolist.service;

import com.andrey.todolist.entity.User;

import java.security.Principal;

public interface UserService {
    public Iterable<User> findAll();
    public User findByUsername(String name);
    public User findById(Long id);
    public User register(User user);
    public User saveUser(User user);
    public void deleteUser(User user);
    public User findLoggedInUser(Principal principal);

}
