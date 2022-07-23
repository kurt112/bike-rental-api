package com.thesis.bikerental.portfolio.bike.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.utils.api.ApiSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BikeServiceImplementation implements BikeService {

    private final BikeRepository repository;
    private int totalPages = 0;
    private long totalElements = 0;
    private int currentPages = 0;

    @Autowired
    public BikeServiceImplementation(BikeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Bike> data (String search, int page, int size, int status) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Bike> pages = repository.findAll(pageable);
        return pages.getContent();
    }

    @Override
    public Bike save(Bike bike) {
        try {
            repository.save(bike);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return bike;
    }

    @Override
    public boolean deleteById(long id) {

        Optional<Bike> bike = repository.findById(id);

        if(bike.isEmpty()) return false;

        repository.deleteById(bike.get().getId());

        return true;
    }

    @Override
    public Bike findById(long id) {
        Optional<Bike> bike = repository.findById(id);
        return bike.orElse(null);
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
