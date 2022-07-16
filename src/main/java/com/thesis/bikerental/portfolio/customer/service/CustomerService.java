package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService extends ServiceGraphQL<Customer> {
}
