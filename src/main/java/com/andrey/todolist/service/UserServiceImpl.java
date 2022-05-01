package com.andrey.todolist.service;

import com.andrey.todolist.dao.UserRepo;
import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.exceptions.UserAlreadyDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findByName(String name) {
        return userRepo.getByUsername(name);
    }

    @Override
    public User saveUser(User user) {
        String pw_hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(pw_hash);
        return userRepo.save(user);
    }

    @Override
    public void deleteCurrentlyLoggedInUser(Principal principal) {
        if (findLoggedInUser(principal) == null) {
            throw new UserAlreadyDeletedException();
        }
        userRepo.delete(findLoggedInUser(principal));
    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @Override
    public Iterable<ToDoItem> getAllToDoItems(Principal principal) {
        return findLoggedInUser(principal).getToDoLists();
    }

    @Override
    public User findLoggedInUser(Principal principal) {
        return userRepo.getByUsername(principal.getName());
    }

    private boolean userWithThatUsernameAlreadyExists(User user) {
        return userRepo.getByUsername(user.getUsername()) != null;
    }
}
