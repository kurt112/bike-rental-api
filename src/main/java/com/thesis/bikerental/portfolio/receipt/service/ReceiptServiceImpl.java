package com.thesis.bikerental.portfolio.receipt.service;

import com.thesis.bikerental.portfolio.receipt.domain.Receipt;
import com.thesis.bikerental.utils.api.ApiSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ReceiptServiceImpl implements ReceiptService{

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public List<Receipt> data(String search, int page, int size, int status) {
        return null;
    }

    @Override
    public Receipt save(Receipt receipt) {

        try {
            receiptRepository.save(receipt);
        }catch (Exception e) {
            return null;
        }

        return receipt;
    }

    @Override
    public boolean deleteById(long id) {
        Receipt receipt = findById(id);
        if(receipt == null) return false;
        receiptRepository.deleteById(id);
        return true;
    }

    @Override
    public Receipt findById(long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        return receipt.orElse(null);
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
