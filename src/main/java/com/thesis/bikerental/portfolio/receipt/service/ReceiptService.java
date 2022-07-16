package com.thesis.bikerental.portfolio.receipt.service;

import com.thesis.bikerental.portfolio.receipt.domain.Receipt;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptService extends ServiceGraphQL<Receipt> {
}
