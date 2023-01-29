package com.thesis.bikerental.portfolio.notification.service;

import com.thesis.bikerental.portfolio.notification.domain.Notification;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService extends ServiceGraphQL<Notification> {
    List<Notification> notificationPerUser(String token, int page, int size);
}
