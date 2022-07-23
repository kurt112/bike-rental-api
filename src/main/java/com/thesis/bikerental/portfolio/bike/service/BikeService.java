package com.thesis.bikerental.portfolio.bike.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.stereotype.Service;

@Service
public interface BikeService extends ServiceGraphQL<Bike> {

}
