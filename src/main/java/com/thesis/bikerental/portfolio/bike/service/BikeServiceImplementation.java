package com.thesis.bikerental.portfolio.bike.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.domain.BikePicture;
import com.thesis.bikerental.portfolio.bike.domain.BikePictureData;
import com.thesis.bikerental.portfolio.customer.service.CustomerRepository;
import com.thesis.bikerental.utils.Jwt;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.BlobProxy;
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
@RequiredArgsConstructor
public class BikeServiceImplementation implements BikeService {

    private final BikeRepository repository;
    private final BikePictureRepository bikePictureRepository;
    private final CustomerRepository customerRepository;
    private final Jwt jwt;

    private ApiSettings apiSettings = new ApiSettings(0,0,0,0,0);


    //available
    @Override
    public List<Bike> data (String search, int page, int size, int status) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Bike> pages = repository.getAllBike(status,search,pageable);

        apiSettings.initApiSettings(size,page,pages.getTotalPages(),pages.getTotalElements());

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
            System.out.println(BlobProxy.generateProxy(bike.get().getBikePictures().get(0).getImage()).toString());
        }
        return bike.orElse(null);
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
    public BikePictureData getBikeImage(long id) {
        Optional<BikePicture> bikePicture = bikePictureRepository.findById(id);

        if(bikePicture.isEmpty()) return null;
        BikePicture picture = bikePicture.get();

        BikePictureData bikePictureData = new BikePictureData(picture.getId(),Base64Utils.encodeToString(picture.getImage()));

        return bikePictureData;
    }

    @Override
    public List<Bike> getBikeRentedByCustomer(String search, int page, int size, String token) {
        Pageable pageable = PageRequest.of(page,size);

        String email = jwt.getUsername(token);

        Page<Bike> pages = repository.getBikeCustomer(pageable,search, 2, email);


        return pages.getContent();
    }

    @Override
    public List<Bike> getBikeRequestedByCustomer(String search, int page, int size, String token) {
        /**
         * TODO: get the token of the user
         */
        Pageable pageable = PageRequest.of(page,size);

        String email = jwt.getUsername(token);
        Page<Bike> pages = repository.getBikeCustomer(pageable,search, 1,email);

        return pages.getContent();
    }
}
