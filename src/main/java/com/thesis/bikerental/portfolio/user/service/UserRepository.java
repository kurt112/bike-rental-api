package com.thesis.bikerental.portfolio.user.service;

import com.thesis.bikerental.portfolio.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select T from User T where T.email = ?1")
    User findByEmail(String email);
}
