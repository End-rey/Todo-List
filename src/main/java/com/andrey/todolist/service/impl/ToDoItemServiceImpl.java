package com.andrey.todolist.service.impl;

import com.andrey.todolist.ExceptionHandler.exceptions.UserAccessException;
import com.andrey.todolist.dto.ToDoItemDto;
import com.andrey.todolist.entity.Status;
import com.andrey.todolist.repository.ToDoItemRepo;
import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.service.ToDoItemService;
import com.andrey.todolist.service.UserService;
import com.andrey.todolist.utils.ConvertToTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;


@Service
@Transactional
public class ToDoItemServiceImpl implements ToDoItemService {
    @Autowired
    private ToDoItemRepo toDoItemRepo;

    @Autowired
    private UserService userService;

    @Override
    public Iterable<ToDoItemDto> findAllByUser(Principal principal) {
        User user = userService.findLoggedInUser(principal);
        return user.getToDoLists().stream()
                .map(ConvertToTO::toDoToTO)
                .collect(Collectors.toList());
    }

    @Override
    public ToDoItemDto addToDoItem(ToDoItemDto toDoItem, Principal principal) {
        User currentUser = userService.findLoggedInUser(principal);
        ToDoItem newToDoItem = ToDoItem.builder()
                .description(toDoItem.getDescription())
                .done(toDoItem.isDone())
                .build();
        newToDoItem.setCreated(new Date());
        newToDoItem.setUpdated(new Date());
        newToDoItem.setStatus(Status.ACTIVE);
        currentUser.addToDoItem(newToDoItem);
        return ConvertToTO.toDoToTO(toDoItemRepo.save(newToDoItem));
    }

    @Override
    public ToDoItemDto editToDo(ToDoItemDto newToDoItem, Principal principal) {
        Long id = newToDoItem.getId();
        if (isToDoAccessible(id, principal)) {
            ToDoItem toDoItem = getToDoItemById(id);
            toDoItem.setDescription(Optional.ofNullable(newToDoItem.getDescription()).orElseGet(() -> toDoItem.getDescription()));
            toDoItem.setDone(newToDoItem.isDone());
            toDoItem.setUpdated(new Date());
            return ConvertToTO.toDoToTO(toDoItemRepo.save(toDoItem));
        }
        return null;
    }

    @Override
    public void deleteToDo(Long id, Principal principal) {
        if(isToDoAccessible(id, principal)) {
            toDoItemRepo.deleteById(id);
        }
    }

    @Override
    public ToDoItemDto findToDoItemById(Long id) {
        return ConvertToTO.toDoToTO(getToDoItemById(id));
    }
    
    private ToDoItem getToDoItemById(Long id) {
        return toDoItemRepo.findById(id).orElseThrow(
            () -> new EntityNotFoundException("ToDo with id " + id + " not found")
        );
    }

    private boolean isToDoAccessible(Long id, Principal principal) {
        User currentUser = userService.findLoggedInUser(principal);
        ToDoItem toDoFromDb = getToDoItemById(id);
        if (!canUserAccessToDo(toDoFromDb, currentUser)) {
            throw new UserAccessException("ToDo not from this user");
        }
        return true;
    }
    
    private boolean canUserAccessToDo(ToDoItem toDoItem, User user) {
        Iterable<ToDoItem> toDoList = user.getToDoLists();
        for (ToDoItem item: toDoList){
            if(item == toDoItem){
                return true;
            }
        }
        return false;
    }
}
