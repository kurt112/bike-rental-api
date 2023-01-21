package com.thesis.bikerental.portfolio.user.service;

import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface UserService extends ServiceGraphQL<User> {
    User findByEmail(String email);

    User findByCellphone(String cellphone);

    void validateUser(HashMap<String, Object> validation, User user);
}
