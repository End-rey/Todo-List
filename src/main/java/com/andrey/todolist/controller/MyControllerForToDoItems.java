package com.andrey.todolist.controller;

import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.exceptions.ToDoItemNotFoundException;
import com.andrey.todolist.exceptions.UserAccessException;
import com.andrey.todolist.service.ToDoItemService;
import com.andrey.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/todos")
public class MyControllerForToDoItems {

    @Autowired
    private ToDoItemService toDoItemService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Iterable<ToDoItem> showAllToDoItems(Principal principal){
        return toDoItemService.findAllByUser(principal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ToDoItem addNewToDoItem(@RequestBody ToDoItem toDoItem, Principal principal){
        return toDoItemService.addToDoItem(toDoItem, principal);
    }

    @PutMapping
    public ToDoItem updateToDoItem(@RequestBody ToDoItem toDoItem, Principal principal){
        return toDoItemService.editToDo(toDoItem, principal);
    }

    @DeleteMapping("/{id}")
    public String deleteToDoItem(@PathVariable int id, Principal principal){
        toDoItemService.deleteToDo(id, principal);
        return "The ToDOItem with id = " + id + " was deleted";
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity completeToDoItem(@PathVariable int id, Principal principal){
        toDoItemService.completeToDo(id, principal);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
