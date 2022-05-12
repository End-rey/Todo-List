package com.andrey.todolist.controller;

import com.andrey.todolist.dto.ToDoItemDto;
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

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
public class MyControllerForToDoItems {

    @Autowired
    private ToDoItemService toDoItemService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<ToDoItemDto>> showAllToDoItems(Principal principal){
        List<ToDoItem> toDoItems = (List<ToDoItem>) toDoItemService.findAllByUser(principal);
        List<ToDoItemDto> toDoItemDtos = toDoItems.stream().map(item -> {
            return new ToDoItemDto(item.getId(), item.getDescription(), item.isDone());
        }).collect(Collectors.toList());

        return ResponseEntity.ok(toDoItemDtos);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ToDoItemDto> addNewToDoItem(@RequestBody ToDoItem toDoItem, Principal principal){
        ToDoItem toDoItem1 = toDoItemService.addToDoItem(toDoItem, principal);
        ToDoItemDto toDoItemDto = new ToDoItemDto(toDoItem1.getId(), toDoItem1.getDescription(),
                toDoItem1.isDone());
        return ResponseEntity.ok(toDoItemDto);
    }

    @PutMapping
    public ResponseEntity<ToDoItemDto> updateToDoItem(@RequestBody ToDoItem toDoItem, Principal principal){
        ToDoItem toDoItem1 = toDoItemService.editToDo(toDoItem, principal);
        ToDoItemDto toDoItemDto = new ToDoItemDto(toDoItem1.getId(), toDoItem1.getDescription(),
                toDoItem1.isDone());
        return ResponseEntity.ok(toDoItemDto);
    }

    @DeleteMapping("/{id}")
    public String deleteToDoItem(@PathVariable Long id, Principal principal){
        toDoItemService.deleteToDo(id, principal);
        return "The ToDOItem with id = " + id + " was deleted";
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity completeToDoItem(@PathVariable Long id, Principal principal){
        toDoItemService.completeToDo(id, principal);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
