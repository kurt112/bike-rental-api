package com.thesis.bikerental.portfolio.bike.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface BikeService extends ServiceGraphQL<Bike> {


    List<Bike> getBikeByCustomer(String token);

    Boolean rentBikeByCustomer(long userId, long BikeId);

    Boolean requestBikeByCustomer(String token, long BikeId,  Bike bike, Date start, Date end);

    Boolean cancelRequestBikeByCustomer(String token, long bikeId);

    Boolean terminateRentedBikeByCustomer(long userId, long bikeId);

    ResponseEntity<?> uploadBikePicture(String pictureName, long bikeId);

    Boolean rejectBikeRequest(long userId, long bikeId);

    Boolean updateBikeLocationByUser(String token, String lng, String lat);

}
