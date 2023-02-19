package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.customer.domain.CustomerReceipt;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.stereotype.Service;

@Service
public interface CustomerReceiptService extends ServiceGraphQL<CustomerReceipt> {

    Bike getRequestBikeCustomer(long id);

}
