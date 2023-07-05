package com.andrey.todolist.controller;

import com.andrey.todolist.dto.ToDoItemDto;
import com.andrey.todolist.service.ToDoItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/data/todos")
@SecurityRequirement(name = "todo-api")
public class MyControllerForToDoItems {

    @Autowired
    private ToDoItemService toDoItemService;

    @GetMapping
    public Iterable<ToDoItemDto> showAllToDoItems(Principal principal){
        return toDoItemService.findAllByUser(principal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ToDoItemDto addNewToDoItem(@RequestBody ToDoItemDto toDoItem, Principal principal){
        return toDoItemService.addToDoItem(toDoItem, principal);
    }

    @PutMapping
    public ToDoItemDto updateToDoItem(@RequestBody ToDoItemDto toDoItem, Principal principal){
        return toDoItemService.editToDo(toDoItem, principal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteToDoItem(@PathVariable Long id, Principal principal){
        toDoItemService.deleteToDo(id, principal);
    }
}
