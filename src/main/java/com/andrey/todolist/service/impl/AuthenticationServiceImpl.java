package com.andrey.todolist.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.andrey.todolist.ExceptionHandler.exceptions.TokenRefreshException;
import com.andrey.todolist.ExceptionHandler.exceptions.UsernameAlreadyExistsException;
import com.andrey.todolist.dto.AuthenticationRequestDto;
import com.andrey.todolist.dto.AuthenticationResponseDto;
import com.andrey.todolist.dto.TokenRefreshRequestDto;
import com.andrey.todolist.dto.UserRequestDto;
import com.andrey.todolist.dto.UserResponseDto;
import com.andrey.todolist.entity.RefreshToken;
import com.andrey.todolist.entity.Status;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.repository.RoleRepo;
import com.andrey.todolist.repository.UserRepo;
import com.andrey.todolist.security.jwt.JwtTokenProvider;
import com.andrey.todolist.service.AuthenticationService;
import com.andrey.todolist.service.RefreshTokenService;
import com.andrey.todolist.utils.ConvertToTO;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepo userRepo;
    private RefreshTokenService refreshTokenService;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserRepo userRepo, RefreshTokenService refreshTokenService,
            RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
        this.refreshTokenService = refreshTokenService;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto) {
        String username = requestDto.getUsername();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

        User user = Optional.ofNullable(userRepo.findByUsername(username)).orElseThrow(
                () -> new UsernameNotFoundException("User with username: " + username + " not found"));

        String accessToken = jwtTokenProvider.createToken(username, user.getRoles());
        RefreshToken refreshToken = Optional.ofNullable(refreshTokenService.findByUsername(username))
                .orElse(refreshTokenService.createRefreshToken(user.getId()));

        return AuthenticationResponseDto.builder()
                .username(username)
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();

    }

    @Override
    public AuthenticationResponseDto refreshToken(TokenRefreshRequestDto requestDto) {
        String requestRefreshToken = requestDto.getRefreshToken();

        RefreshToken refreshToken = Optional.ofNullable(refreshTokenService.findByToken(requestRefreshToken))
                .orElseThrow(() -> new TokenRefreshException(
                        "Refresh token " + requestRefreshToken + " is not in database"));

        refreshToken = refreshTokenService.verifyExpiration(refreshToken);

        User user = refreshToken.getUser();

        String accessToken = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        return AuthenticationResponseDto.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    public UserResponseDto register(UserRequestDto requestDto) {
        if (userRepo.findByUsername(requestDto.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("User with username " + requestDto.getUsername() + " already exists");
        }
        User user = User.builder()
                        .username(requestDto.getUsername())
                        .firstName(requestDto.getFirstName())
                        .lastName(requestDto.getLastName())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .roles(Arrays.asList(roleRepo.findByName("ROLE_USER")))
                        .build();
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());

        return ConvertToTO.userToTO(userRepo.save(user));
    }

}
