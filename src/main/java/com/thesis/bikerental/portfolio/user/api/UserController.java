package com.thesis.bikerental.portfolio.user.api;

import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.portfolio.user.service.UserService;
import com.thesis.bikerental.utils.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final Jwt jwt;

    @PatchMapping
    public ResponseEntity<HashMap<String, ?>> updateUser(@RequestBody User user){
        HashMap<String ,?> hashMap = new HashMap<>();

        userService.save(user);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> uploadValidIdUser(@RequestParam("userID") long id, @RequestParam("validID") String validID){

        return userService.uploadValidId(id, validID);
    }



    @PatchMapping("/password/{token}")
    public ResponseEntity<?> UpdatePasswordUser(@RequestParam("newPass") String newPass,
                                                @RequestParam("currentPass") String currentPass,
                                                @PathVariable("token") String token
    ){

        return userService.updatePassword(token,currentPass,newPass);
    }


}
