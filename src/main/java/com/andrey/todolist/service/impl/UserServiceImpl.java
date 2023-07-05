package com.andrey.todolist.service.impl;

import com.andrey.todolist.dto.UserRequestDto;
import com.andrey.todolist.dto.UserResponseDto;
import com.andrey.todolist.repository.UserRepo;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.service.UserService;
import com.andrey.todolist.utils.ConvertToTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Iterable<UserResponseDto> findAll() {
        return userRepo.findAll().stream()
                .map(ConvertToTO::userToTO)
                .collect(Collectors.toSet());
    }

    @Override
    public UserResponseDto findByUsername(String username) {
        return ConvertToTO.userToTO(Optional.ofNullable(userRepo.findByUsername(username)).orElseThrow(
            () -> new EntityNotFoundException("User with username " + username + " not found")
        ));
    }

    @Override
    public UserResponseDto findById(Long id) {
        return ConvertToTO.userToTO(userRepo.findById(id).orElseThrow(
            () -> new EntityNotFoundException("User with id" + id + " not found")
        ));
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto requestDto) {
        User user = Optional.ofNullable(userRepo.findByUsername(requestDto.getUsername()))
            .orElseThrow(() -> new EntityNotFoundException("User with username " + requestDto.getUsername() + " not found"));
        user.setPassword(Optional.ofNullable(passwordEncoder.encode(user.getPassword())).orElseGet(() -> user.getPassword()));
        user.setFirstName(Optional.ofNullable(requestDto.getFirstName()).orElseGet(() -> user.getFirstName()));
        user.setLastName(Optional.ofNullable(requestDto.getLastName()).orElseGet(() -> user.getLastName()));
        return ConvertToTO.userToTO(userRepo.save(user));
    }

    public User findLoggedInUser(Principal principal) {
        return userRepo.findByUsername(principal.getName());
    }

    @Override
    public void deleteUserByUsername(String username) {
        User user = Optional.ofNullable(userRepo.findByUsername(username)).orElseThrow(
            () -> new EntityNotFoundException("User with username " + username + " not found")
        );
        userRepo.delete(user);
    }

    // public void deleteCurrentlyLoggedInUser(Principal principal) {
    // if (findLoggedInUser(principal) == null) {
    // throw new UserAlreadyDeletedException();
    // }
    // userRepo.delete(findLoggedInUser(principal));
    // }
}
