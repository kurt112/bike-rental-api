package com.thesis.bikerental.portfolio.user.api;

import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.portfolio.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @PatchMapping
    public ResponseEntity<HashMap<String, ?>> updateUser(@RequestBody User user){
        HashMap<String ,?> hashMap = new HashMap<>();

        userService.save(user);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

}
