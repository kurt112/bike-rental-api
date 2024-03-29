package com.thesis.bikerental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BikeRentalApplication {
	public static void main(String[] args) {
		SpringApplication.run(BikeRentalApplication.class, args);
	}
}
