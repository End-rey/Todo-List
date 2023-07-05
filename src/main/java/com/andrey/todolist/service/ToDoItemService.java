package com.andrey.todolist.service;

import com.andrey.todolist.dto.ToDoItemDto;

import java.security.Principal;

public interface ToDoItemService {
    Iterable<ToDoItemDto> findAllByUser(Principal principal);
    ToDoItemDto addToDoItem(ToDoItemDto toDoItem, Principal principal);
    ToDoItemDto editToDo(ToDoItemDto newToDoItem, Principal principal);
    void deleteToDo(Long id, Principal principal);
    ToDoItemDto findToDoItemById(Long id);
}
