package com.andrey.todolist.repository;

import com.andrey.todolist.entity.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoItemRepo extends JpaRepository<ToDoItem, Long> {
    public void delete(ToDoItem toDoItem);
    public void deleteById(Long id);
}
