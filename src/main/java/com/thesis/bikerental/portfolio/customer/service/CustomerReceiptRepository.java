package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.customer.domain.CustomerReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerReceiptRepository extends JpaRepository<CustomerReceipt, Long> {
    @Query(value = "SELECT e from Bike e where e.status = 1 and e.assignedCustomer.id=?1")
    Bike getRequestBikeCustomer(long id);
}
