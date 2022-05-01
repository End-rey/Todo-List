package com.andrey.todolist.service;

import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;

import java.security.Principal;
import java.util.List;

public interface ToDoItemService {
    public Iterable<ToDoItem> findAllByUser(Principal principal);
    public ToDoItem addToDoItem(ToDoItem toDoItem, Principal principal);
    public ToDoItem editToDo(ToDoItem newToDoItem, Principal principal);
    public void deleteToDo(int id, Principal principal);
    public void completeToDo(int id, Principal principal);
    public boolean toDoExists(int id);
    public ToDoItem findToDoItemById(int id);
    public boolean canUserAccessToDo(ToDoItem toDoItem, User user);
}
