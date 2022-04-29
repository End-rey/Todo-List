package com.andrey.todolist.dao;

import com.andrey.todolist.entity.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface ToDoItemRepo extends JpaRepository<ToDoItem, Integer> {
}
