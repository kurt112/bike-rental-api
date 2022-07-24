package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
