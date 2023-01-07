package com.thesis.bikerental.portfolio.notification.service;

import com.thesis.bikerental.portfolio.notification.domain.Notification;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService extends ServiceGraphQL<Notification> {
}
