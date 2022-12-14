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

    List<Bike> getBikeByCustomer(String search,String token);

    Boolean rentBikeByCustomer(String token, long BikeId);

}