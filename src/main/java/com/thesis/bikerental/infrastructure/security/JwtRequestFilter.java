package com.thesis.bikerental.infrastructure.security;

import com.thesis.bikerental.portfolio.user.service.UserDetailsService;
import com.thesis.bikerental.utils.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtRequestFilter {


//    private final UserDetailsService userDetailsService;
//    private final Jwt jwt;

//    @Autowired
//    public JwtRequestFilter(UserDetailsService userDetailsService, Jwt jwt) {
//        System.out.println(jwt);
//        this.userDetailsService = userDetailsService;
//        this.jwt = jwt;
//    }

}