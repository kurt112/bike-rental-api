package com.thesis.bikerental.portfolio.store.service;

import com.thesis.bikerental.portfolio.store.domain.Store;
import com.thesis.bikerental.utils.api.ApiSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class StoreServiceImpl implements StoreService {

    private StoreRepository repository;

    @Autowired
    public StoreServiceImpl(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Store> data(String search, int page, int size, int status) {
        return repository.findAll();
    }

    @Override
    public Store save(Store store) {
        try {
            repository.save(store);
        }catch (Exception e){
            return null;
        }

        return store;
    }

    @Override
    public boolean deleteById(long id) {
        Store store = findById(id);

        if(store == null) return false;

        repository.deleteById(id);
        return true;
    }

    @Override
    public Store findById(long id) {
        Optional<Store> store  = repository.findById(id);
        return store.orElse(null);
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
