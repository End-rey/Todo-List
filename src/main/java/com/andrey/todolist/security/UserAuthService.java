package com.andrey.todolist.security;

import com.andrey.todolist.repository.UserRepo;
import com.andrey.todolist.entity.Role;
import com.andrey.todolist.entity.User;
import com.andrey.todolist.security.jwt.JwtUser;
import com.andrey.todolist.security.jwt.JwtUserFactory;
import com.andrey.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserAuthService implements UserDetailsService {

    private final UserRepo userService;

    @Autowired
    UserAuthService(UserRepo userService) {
        this.userService = userService;
    }

//    private User findByUsername(String username){
//        return userService.findByUsername(username);
//    }

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
