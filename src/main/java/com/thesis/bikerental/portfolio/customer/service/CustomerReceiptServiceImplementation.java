package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.customer.domain.CustomerReceipt;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Pageable pageable = PageRequest.of(page-1,size,Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<CustomerReceipt> pages = customerReceiptRepository.findAll(pageable);
        System.out.println(pages.getTotalElements());
        apiSettings.initApiSettings(size,page,pages.getTotalPages(),pages.getTotalElements());
        return pages.getContent();
    }

    @Override
    public CustomerReceipt save(CustomerReceipt customerReceipt) {
        try {
            Bike bike = getRequestBikeCustomer(customerReceipt.getCustomer().getId());
            customerReceipt.setBike(bike);
            customerReceipt.setActive(true);
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

    @Override
    public Bike getRequestBikeCustomer(long id) {
        return customerReceiptRepository.getRequestBikeCustomer(id);
    }
}
