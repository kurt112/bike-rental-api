package com.thesis.bikerental.portfolio.notification.api;

import com.thesis.bikerental.portfolio.notification.domain.Notification;
import com.thesis.bikerental.portfolio.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @SchemaMapping(typeName = "Query",value = "getNotifications")
    private List<Notification> getNotifications (@Argument("size") int size,
                                                 @Argument("page") int page,
                                                 @Argument("token") String token){
        return notificationService.notificationPerUser(token,page,size);
    }
}
