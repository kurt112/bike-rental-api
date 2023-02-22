package com.thesis.bikerental.infrastructure.controller;

import com.thesis.bikerental.infrastructure.security.AuthenticationRequest;
import com.thesis.bikerental.portfolio.user.service.UserDetailsService;
import com.thesis.bikerental.portfolio.user.service.UserService;
import com.thesis.bikerental.utils.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final Jwt jwt;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest credentials){

        HashMap<String, Object> result = new HashMap<>();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),credentials.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            result.put("message", "Account Not Found");
            return ResponseEntity.badRequest().body(result);
        }catch (DisabledException disabledException){
            result.put("message", "Please Verify Your Email First");
            return ResponseEntity.badRequest().body(result);
        }catch (Exception e){
            e.printStackTrace();
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());
        userDetailsService.getUser().setCustomer(null);
        userDetailsService.getUser().setEmployee(null);
        final String jwt = this.jwt.generateToken(userDetails, false);
        result.put("token", jwt);
        result.put("message", "Login Successful");
        result.put("user", userDetailsService.getUser());

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String token){

        HashMap<String, String> result = new HashMap<>();

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    @SchemaMapping(typeName = "Query",value = "getUserById")
    public com.thesis.bikerental.portfolio.user.domain.User getUserByToken(@Argument String token){

        String email = jwt.getUsername(token);
        com.thesis.bikerental.portfolio.user.domain.User user = userService.findByEmail(email);

        return user;
    }
}
