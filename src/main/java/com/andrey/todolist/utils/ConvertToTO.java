package com.andrey.todolist.utils;

import com.andrey.todolist.dto.ToDoItemDto;
import com.andrey.todolist.dto.UserResponseDto;
import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;

public class ConvertToTO {
    private ConvertToTO() {

    }

    public static UserResponseDto userToTO(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static ToDoItemDto toDoToTO(ToDoItem toDoItem) {
        return ToDoItemDto.builder()
                .id(toDoItem.getId())
                .description(toDoItem.getDescription())
                .done(toDoItem.isDone())
                .build();
    }
}
