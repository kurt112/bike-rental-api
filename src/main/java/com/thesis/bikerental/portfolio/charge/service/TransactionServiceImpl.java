package com.thesis.bikerental.portfolio.charge.service;

import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<com.thesis.bikerental.portfolio.charge.domain.Transaction> data(String search, int page, int size, int status) {
        return null;
    }

    @Override
    public com.thesis.bikerental.portfolio.charge.domain.Transaction save(com.thesis.bikerental.portfolio.charge.domain.Transaction transaction) {
        try {
            transactionRepository.saveAndFlush(transaction);
        }catch (Exception e){
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public boolean deleteById(long id) {
        if(findById(id) == null) return false;

        transactionRepository.deleteById(id);
        return true;
    }

    @Override
    public com.thesis.bikerental.portfolio.charge.domain.Transaction findById(long id) {
        return transactionRepository.findById(id).orElse(null);
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
