package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.customer.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT c FROM Customer c WHERE c.isActive = true AND c.user.firstName LIKE  %:search% ORDER BY c.toPay DESC")
    Page<Customer> getCustomers(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT c.toPay from Customer c where c.user.id = ?1")
    Customer getCustomerByUserId(long id);
}
