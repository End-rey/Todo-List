package com.andrey.todolist.dto;

import lombok.Data;

@Data
public class ToDoItemDto {
    private Long id;
    private String description;
    private boolean done;

    public ToDoItemDto(Long id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public ToDoItemDto() {
    }
}
