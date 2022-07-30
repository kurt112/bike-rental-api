package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
