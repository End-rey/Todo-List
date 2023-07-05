package com.andrey.todolist.ExceptionHandler.exceptions;

public class UserAccessException extends RuntimeException {
    public UserAccessException(String message) {
        super(message);
    }
}
