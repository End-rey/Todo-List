package com.andrey.todolist.service;

import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;

import java.security.Principal;

public interface ToDoItemService {
    public Iterable<ToDoItem> findAllByUser(Principal principal);
    public ToDoItem addToDoItem(ToDoItem toDoItem, Principal principal);
    public ToDoItem editToDo(ToDoItem newToDoItem, Principal principal);
    public void deleteToDo(Long id, Principal principal);
    public void completeToDo(Long id, Principal principal);
    public boolean toDoExists(Long id);
    public ToDoItem findToDoItemById(Long id);
    public boolean canUserAccessToDo(ToDoItem toDoItem, User user);
}
