package com.thesis.bikerental.portfolio.notification.service;

import com.thesis.bikerental.portfolio.notification.domain.Notification;
import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.portfolio.user.service.UserRepository;
import com.thesis.bikerental.utils.Jwt;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Getter
@Transactional
@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository repository;
    private final UserRepository userRepository;
    private final User system;
    private final Jwt jwt;

    private final long systemId;
    @Autowired
    public NotificationServiceImpl(NotificationRepository repository, UserRepository userRepository, Jwt jwt,  @Value("${system.id}") long systemId) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.jwt = jwt;
        this.systemId = systemId;
        system = userRepository.findById(systemId).orElse(null);
    }


    @Override
    public List<Notification> data(String search, int page, int size, int status) {
        Pageable pageable = PageRequest.of(page-1,size);
//        Page<Notification> pages = repository
        return repository.findAll();
    }

    @Override
    public List<Notification> notificationPerUser(String token, int page, int size) {
        String email = jwt.getUsername(token);

        if(email == null) return null;

        User user = userRepository.findFirstByEmail(email);

        if(user == null) return null;

        Pageable pageable = PageRequest.of(page-1,size);

        long id = user.getId();

        if(user.getUserRole().equals("admin") || user.getUserRole().equals("employee")){
            id = systemId;
        }

        Page<Notification> notificationsUser = repository.getAllRecentNotification(id,pageable);

        return notificationsUser.getContent();
    }

    @Override
    public Notification save(Notification notification) {
        if(notification.getTo() == null) notification.setTo(system);
        if(notification.getFrom() == null) notification.setFrom(system);

        try {
            repository.save(notification);
        }catch (Exception e){
            e.printStackTrace();
        }
        return notification;
    }

    @Override
    public boolean deleteById(long id) {

        if(findById(id) == null) return false;

        repository.deleteById(id);
        return true;
    }

    @Override
    public Notification findById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ApiSettings apiSettings() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }


}
