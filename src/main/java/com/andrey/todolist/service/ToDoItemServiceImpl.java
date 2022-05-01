package com.andrey.todolist.service;

import com.andrey.todolist.dao.ToDoItemRepo;
import com.andrey.todolist.entity.ToDoItem;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.exceptions.ToDoItemNotFoundException;
import com.andrey.todolist.exceptions.UserAccessException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;


@Service
@Transactional
public class ToDoItemServiceImpl implements ToDoItemService {
    @Autowired
    private ToDoItemRepo toDoItemRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionManager transactionManager;

    @Override
    public Iterable<ToDoItem> findAllByUser(Principal principal) {
        User user = userService.findLoggedInUser(principal);
        return user.getToDoLists();
    }

    @Override
    public ToDoItem addToDoItem(ToDoItem toDoItem, Principal principal) {
        User currentUser = userService.findLoggedInUser(principal);
        currentUser.addToDoItem(toDoItem);
        return toDoItemRepo.save(toDoItem);
    }

    @Override
    public ToDoItem editToDo(ToDoItem newToDoItem, Principal principal) {
        int id = newToDoItem.getId();
        if (isToDoAccessible(id, principal)) {
            ToDoItem toDoItem = findToDoItemById(id);
            toDoItem.setDescription(newToDoItem.getDescription());
            toDoItem.setDone(newToDoItem.isDone());
            return toDoItemRepo.save(toDoItem);
        }
        return null;
    }

    @Override
    public void deleteToDo(int id, Principal principal) {
        if(isToDoAccessible(id, principal)) {
//            ToDoItem toDoItem = findToDoItemById(id);

            toDoItemRepo.deleteById(id);
        }
    }

    @Override
    public void completeToDo(int id, Principal principal) {
        if(isToDoAccessible(id, principal)) {
            findToDoItemById(id).setDone(true);
        }
    }

    private boolean isToDoAccessible(int id, Principal principal) {
        User currentUser = userService.findLoggedInUser(principal);
        if (!toDoExists(id)) {
            throw new ToDoItemNotFoundException();
        }
        ToDoItem toDoFromDb = findToDoItemById(id);
        if (!canUserAccessToDo(toDoFromDb, currentUser)) {
            throw new UserAccessException();
        }
        return true;
    }

    @Override
    public boolean toDoExists(int id) {
        ToDoItem toDoItem = findToDoItemById(id);
        if (toDoItem != null)
            return true;
        else
            return false;
    }

    @Override
    public boolean canUserAccessToDo(ToDoItem toDoItem, User user) {
        Iterable<ToDoItem> toDoList = user.getToDoLists();
        for (ToDoItem item: toDoList){
            if(item == toDoItem){
                return true;
            }
        }
        return false;
    }

    @Override
    public ToDoItem findToDoItemById(int id) {
        ToDoItem toDoItem = toDoItemRepo.findById(id);
        return toDoItem;
    }
}
