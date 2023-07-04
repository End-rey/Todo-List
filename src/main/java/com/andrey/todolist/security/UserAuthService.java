package com.andrey.todolist.security;

import com.andrey.todolist.repository.UserRepo;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.security.jwt.JwtUser;
import com.andrey.todolist.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserAuthService implements UserDetailsService {

    private final UserRepo userService;

    @Autowired
    UserAuthService(UserRepo userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find the user: " + username);
        }

        JwtUser jwtUser = JwtUserFactory.create(user);

        return jwtUser;
    }


}
