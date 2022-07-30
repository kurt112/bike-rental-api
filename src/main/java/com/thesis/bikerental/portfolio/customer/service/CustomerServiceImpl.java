package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.utils.api.ApiSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<Customer> data(String search, int page, int size, int status) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Customer> pages = customerRepository.findAll(pageable);
        return pages.getContent();
    }

    @Override
    public Customer save(Customer customer) {
        try {
            customerRepository.save(customer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public boolean deleteById(long id) {
        Customer customer = findById(id);

        if(customer != null) return false;

        customerRepository.deleteById(id);

        return true;
    }

    @Override
    public Customer findById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    @Override
    public ApiSettings apiSettings() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
