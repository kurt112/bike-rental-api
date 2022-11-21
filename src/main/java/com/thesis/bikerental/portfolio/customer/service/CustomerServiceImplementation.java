package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation implements CustomerService{

    private final CustomerRepository customerRepository;

    private ApiSettings apiSettings = new ApiSettings(0,0,0,0,0);

    @Override
    public List<Customer> data(String search, int page, int size, int status) {
        Pageable pageable = PageRequest.of(page-1,size);

        Page<Customer> pages = customerRepository.getCustomers(search,pageable);

        apiSettings.initApiSettings(size,page,pages.getTotalPages(),pages.getTotalElements());

        return pages.getContent();
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        try {
            customerRepository.saveAndFlush(customer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        Customer customer = findById(id);

        if(customer == null) return false;

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
        return apiSettings;
    }

    @Override
    public long count() {
        return 0;
    }
}
