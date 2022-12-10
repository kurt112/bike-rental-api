package com.thesis.bikerental.portfolio.user.service;

import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public List<User> data(String search, int page, int size, int status) {
        return null;
    }

    @Override
    @Transactional
    public User save(User user) {
        try {
            userRepository.save(user);
        }catch (Exception e){
            return null;
        }

        return user;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public User findById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public ApiSettings apiSettings() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public User findByEmail(String email) {
        System.out.println("The email");
        return userRepository.findByEmail(email);
    }
}
