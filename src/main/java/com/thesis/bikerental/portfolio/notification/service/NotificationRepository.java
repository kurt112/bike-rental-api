package com.thesis.bikerental.portfolio.notification.service;

import com.thesis.bikerental.portfolio.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT e from Notification e where e.to.id = ?1 ORDER BY e.createdAt DESC")
    Page<Notification> getAllRecentNotification(long id,Pageable pageable);
}
