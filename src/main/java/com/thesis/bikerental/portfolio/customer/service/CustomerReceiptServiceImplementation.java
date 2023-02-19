package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.customer.domain.CustomerReceipt;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CustomerReceiptServiceImplementation implements CustomerReceiptService{

    private final CustomerReceiptRepository customerReceiptRepository;
    private final ApiSettings apiSettings = new ApiSettings(0,0,0,0,0);

    @Override
    public List<CustomerReceipt> data(String search, int page, int size, int status) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<CustomerReceipt> pages = customerReceiptRepository.findAll(pageable);
        System.out.println(pages.getTotalElements());
        apiSettings.initApiSettings(size,page,pages.getTotalPages(),pages.getTotalElements());
        return pages.getContent();
    }

    @Override
    public CustomerReceipt save(CustomerReceipt customerReceipt) {
        try {
            Bike bike = customerReceiptRepository.getRequestBikeCustomer(customerReceipt.getCustomer().getId());
            System.out.println(bike);
            System.out.println("the bike " + bike);
            customerReceipt.setBike(bike);
            customerReceiptRepository.save(customerReceipt);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return customerReceipt;
    }

    @Override
    public boolean deleteById(long id) {
        try {
            customerReceiptRepository.deleteById(id);
        }catch (Exception e){

            return false;
        }

        return true;
    }

    @Override
    public CustomerReceipt findById(long id) {
        CustomerReceipt  customerReceipt = customerReceiptRepository.findById(id).orElse(null);
        return customerReceipt;
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
