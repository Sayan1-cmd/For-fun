package com.project.project.service;

import com.project.project.entity.ApplicationUser;
import com.project.project.exception.CustomAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserService userService;

    public UserDetailsServiceImpl(ApplicationUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ApplicationUser user = userService.getUserByUserName(userName);
        if (user == null) {
            throw new CustomAuthenticationException("Invalid credentials!");
        } else {
            return new UserServiceImpl(user);
        }

    }
}