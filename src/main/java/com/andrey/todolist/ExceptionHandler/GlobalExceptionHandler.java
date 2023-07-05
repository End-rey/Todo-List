package com.andrey.todolist.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.andrey.todolist.ExceptionHandler.error.AppError;
import com.andrey.todolist.ExceptionHandler.exceptions.JwtAuthenticationException;
import com.andrey.todolist.ExceptionHandler.exceptions.TokenRefreshException;
import com.andrey.todolist.ExceptionHandler.exceptions.UserAccessException;
import com.andrey.todolist.ExceptionHandler.exceptions.UserAlreadyDeletedException;
import com.andrey.todolist.ExceptionHandler.exceptions.UsernameAlreadyExistsException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Internal Server Error"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler
    public ResponseEntity<AppError> catchJwtAuthenticationException(JwtAuthenticationException e) {
        log.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), e.getLocalizedMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchTokenRefreshException(TokenRefreshException e) {
        log.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), e.getLocalizedMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchUserAccessException(UserAccessException e) {
        log.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), e.getLocalizedMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchUserAlreadyDeletedException(UserAlreadyDeletedException e) {
        log.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        log.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), e.getLocalizedMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchAuthenticationException(AuthenticationException e) {
        log.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), "Invalid username or password"), HttpStatus.FORBIDDEN);
    }
}
