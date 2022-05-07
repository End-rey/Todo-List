package com.andrey.todolist.service.impl;

import com.andrey.todolist.entity.Role;
import com.andrey.todolist.entity.Status;
import com.andrey.todolist.exceptions.UsernameAlreadyExistsException;
import com.andrey.todolist.repository.RoleRepo;
import com.andrey.todolist.repository.UserRepo;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User findById(Long id){
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User register(User user){
        if(userWithThatUsernameAlreadyExists(user)){
            throw new UsernameAlreadyExistsException();
        }
        Role roleUser = roleRepo.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());

        return userRepo.save(user);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

//    public void deleteCurrentlyLoggedInUser(Principal principal) {
//        if (findLoggedInUser(principal) == null) {
//            throw new UserAlreadyDeletedException();
//        }
//        userRepo.delete(findLoggedInUser(principal));
//    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

//    public Iterable<ToDoItem> getAllToDoItems(Principal principal) {
//        return findLoggedInUser(principal).getToDoLists();
//    }

    public User findLoggedInUser(Principal principal) {
        return userRepo.findByUsername(principal.getName());
    }

    private boolean userWithThatUsernameAlreadyExists(User user) {
        return userRepo.findByUsername(user.getUsername()) != null;
    }
}
