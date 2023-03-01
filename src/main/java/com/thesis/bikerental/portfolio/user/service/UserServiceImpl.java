package com.thesis.bikerental.portfolio.user.service;

import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.utils.Jwt;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final Jwt jwt;
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
        return userRepository.findFirstByEmail(email);
    }

    @Override
    public User findByCellphone(String cellphone) {
        return userRepository.findFirstByCellphone(cellphone);
    }

    @Override
    public void validateUser(HashMap<String, Object> validation, User user) {
        User currentUserEmail = findByEmail(user.getEmail());
        User currentUserCellphone = findByCellphone(user.getCellphone());


        if(currentUserEmail != null && currentUserEmail.getId() != user.getId()){
            validation.put("email","Email already exist");
        }

        if(currentUserCellphone != null && currentUserCellphone.getId() != user.getId()){
            validation.put("cellphone","Cellphone number must be unique");
        }
    }

    @Override
    public ResponseEntity<?>  isUserRenting(String token) {
        HashMap<String , Object> result = new HashMap<>();
        String email = jwt.getUsername(token);
        User user = userRepository.findFirstByEmail(email);
        boolean isRenting = user.isRenting();
        result.put("data", isRenting);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> uploadValidId(long userId, String validId) {
        User user = findById(userId);
        HashMap<String , Object> result = new HashMap<>();

        if(user == null){
            result.put("data", "User Not Found");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        user.setValidIdPhoto(validId);
        result.put("data", "Valid Id Added");
        userRepository.save(user);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
