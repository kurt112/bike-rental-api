package com.thesis.bikerental.portfolio.bike.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.domain.BikePicture;
import com.thesis.bikerental.portfolio.bike.domain.BikePictureData;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BikeService extends ServiceGraphQL<Bike> {

    BikePictureData getBikeImage(long pirctureId);

    List<Bike> getBikeRentedByCustomer(String search, int page, int size, String token);
    List<Bike> getBikeRequestedByCustomer(String search, int page, int size, String token);

}
