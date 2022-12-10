package com.thesis.bikerental.portfolio.user.service;


import com.thesis.bikerental.infrastructure.security.UserPrincipal;
import com.thesis.bikerental.portfolio.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserService userService;
    private User user;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println(" i am in find email");
        User user = userService.findByEmail(email);
        System.out.println(user);


        if (user == null ) throw new UsernameNotFoundException(email);
        this.user = user;
        System.out.println("this is user " + user);
        return new UserPrincipal(user);
    }

    public User getUser() {
        return user;
    }
}
