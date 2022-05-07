package com.andrey.todolist.repository;

import com.andrey.todolist.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
