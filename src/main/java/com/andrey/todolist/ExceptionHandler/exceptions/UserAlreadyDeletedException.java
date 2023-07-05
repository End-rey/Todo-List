package com.andrey.todolist.ExceptionHandler.exceptions;

public class UserAlreadyDeletedException extends RuntimeException {
    public UserAlreadyDeletedException(String message) {
        super(message);
    }
}
