package com.thesis.bikerental.portfolio.bike.service;

import com.thesis.bikerental.portfolio.bike.domain.BikePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikePictureRepository extends JpaRepository<BikePicture, Long> {
}
