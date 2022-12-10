package com.thesis.bikerental.portfolio.bike.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {
    @Query(value = "SELECT t FROM Bike t WHERE t.status = ?1 and (t.name like %?2% or t.description like %?2% or t.name like %?2% or t.brand like %?2%)")
    Page<Bike> getAllBike(int status, String search, Pageable pageable);

    @Query(value = "SELECT T FROM Bike T WHERE T.status = 2")
    List<Bike> getBikeWithCustomer();
}
