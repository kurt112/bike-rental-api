package com.thesis.bikerental.portfolio.bike.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.domain.BikePicture;
import com.thesis.bikerental.portfolio.bike.domain.BikePictureData;
import com.thesis.bikerental.utils.api.ApiSettings;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BikeServiceImplementation implements BikeService {

    private final BikeRepository repository;
    private final BikePictureRepository bikePictureRepository;

    private int totalPages = 0;
    private long totalElements = 0;
    private int currentPages = 0;

    @Autowired
    public BikeServiceImplementation(BikeRepository repository, BikePictureRepository bikePictureRepository) {
        this.repository = repository;
        this.bikePictureRepository = bikePictureRepository;
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
        if(!bike.isEmpty()){
//            bike.get().setObject();
            System.out.println(BlobProxy.generateProxy(bike.get().getBikePictures().get(0).getImage()).toString());
        }
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

    @Override
    public BikePictureData getBikeImage(long id) {
        Optional<BikePicture> bikePicture = bikePictureRepository.findById(id);

        if(bikePicture.isEmpty()) return null;
        BikePicture picture = bikePicture.get();

        BikePictureData bikePictureData = new BikePictureData(picture.getId(),Base64Utils.encodeToString(picture.getImage()));

        return bikePictureData;
    }

    @Override
    public List<Bike> getBikeRented() {
        /**
         * TODO: get the token of the user
         */
        return null;
    }

    @Override
    public List<Bike> getBikeRequested() {
        /**
         * TODO: get the token of the user
         */
        return null;
    }
}
