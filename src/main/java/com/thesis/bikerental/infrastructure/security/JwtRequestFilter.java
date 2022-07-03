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

@Service
public class JwtRequestFilter extends OncePerRequestFilter {


//    private final UserDetailsService userDetailsService;
//    private final Jwt jwt;

//    @Autowired
//    public JwtRequestFilter(UserDetailsService userDetailsService, Jwt jwt) {
//        System.out.println(jwt);
//        this.userDetailsService = userDetailsService;
//        this.jwt = jwt;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        final String authorizationHeader = request.getHeader("Authorization");
//
//
//        String username = null;
//        String jwt = null;
//
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
//
//            jwt = authorizationHeader.substring(7);
//            username = this.jwt.getUsername(jwt);
//        }
//
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//
//            if(this.jwt.validateToken(jwt, userDetails)){
//
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//
//
//            }
//        }
//
//        filterChain.doFilter(request,response);
    }
}