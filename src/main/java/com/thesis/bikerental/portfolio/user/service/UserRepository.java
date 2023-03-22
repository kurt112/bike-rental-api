package com.thesis.bikerental.portfolio.user.service;

import com.thesis.bikerental.portfolio.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByEmail(String email);

    User findFirstByCellphone(String cellphone);

    @Query(value = "SELECT c.isRenting  from User c where c.id = ?1")
    boolean isUserRenting(long id);
}
