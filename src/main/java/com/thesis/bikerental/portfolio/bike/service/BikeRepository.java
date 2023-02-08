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
    @Query(value = "SELECT t FROM Bike t WHERE t.status = ?1 and t.isAvailable = true and (t.assignedCustomer.user.lastName like %?2% or t.assignedCustomer.user.firstName like %?2% or t.name like %?2% or t.description like %?2% or t.name like %?2% or t.brand like %?2%)")
    Page<Bike> getAllBike(int status, String search, Pageable pageable);

    @Query(value = "SELECT t FROM Bike t WHERE t.quantity > 0 and t.status = ?1 and t.isAvailable = true and (t.name like %?2% or t.description like %?2% or t.name like %?2% or t.brand like %?2%)")
    Page<Bike> getAllBikeAvailable(int status, String search, Pageable pageable);

    @Query(value = "SELECT t FROM Bike t WHERE  t.status = 2 and t.isAvailable = true")
    List<Bike> getBikeWithCustomer();

    @Query(value = "SELECT t FROM Bike t WHERE t.status = ?1 and t.isAvailable = true")
    List<Bike> getAllBikes(int status);
}
