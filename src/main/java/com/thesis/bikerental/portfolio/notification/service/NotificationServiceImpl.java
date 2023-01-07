package com.thesis.bikerental.portfolio.notification.service;

import com.thesis.bikerental.portfolio.notification.domain.Notification;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository repository;

    @Override
    public List<Notification> data(String search, int page, int size, int status) {
        return repository.findAll();
    }

    @Override
    public Notification save(Notification notification) {

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
