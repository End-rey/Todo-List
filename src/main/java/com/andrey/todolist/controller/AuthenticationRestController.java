package com.andrey.todolist.controller;

import com.andrey.todolist.dto.AuthenticationRequestDto;
import com.andrey.todolist.dto.AuthenticationResponseDto;
import com.andrey.todolist.dto.TokenRefreshRequestDto;
import com.andrey.todolist.entity.RefreshToken;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.exceptions.TokenRefreshException;
import com.andrey.todolist.security.jwt.JwtTokenProvider;
import com.andrey.todolist.service.RefreshTokenService;
import com.andrey.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth/")
public class AuthenticationRestController {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;
    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager,
                                        JwtTokenProvider jwtTokenProvider,
                                        UserService userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequestDto requestDto){
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if(user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String accessToken = jwtTokenProvider.createToken(username, user.getRoles());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

            return ResponseEntity.ok(new AuthenticationResponseDto(username, accessToken, refreshToken.getToken()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequestDto requestDto){
        try {
            String requestRefreshToken = requestDto.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken);
        if(refreshToken == null){
            throw new TokenRefreshException(requestRefreshToken, "Refresh token is not in database");
        }

        refreshTokenService.verifyExpiration(refreshToken);

        User user = refreshToken.getUser();
        String username = user.getUsername();

        String accessToken = jwtTokenProvider.createToken(username, user.getRoles());
        return ResponseEntity.ok(new AuthenticationResponseDto(username, accessToken, refreshToken.getToken()));
        } catch (TokenRefreshException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.register(user);
    }
}
