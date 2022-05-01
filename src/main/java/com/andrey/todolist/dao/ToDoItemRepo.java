package com.andrey.todolist.dao;

import com.andrey.todolist.entity.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoItemRepo extends JpaRepository<ToDoItem, Integer> {
    public ToDoItem findById(int id);
    public void delete(ToDoItem toDoItem);
    public void deleteById(int id);
}
