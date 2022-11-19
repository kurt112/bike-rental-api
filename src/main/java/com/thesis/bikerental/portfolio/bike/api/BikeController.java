package com.thesis.bikerental.portfolio.bike.api;


import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.domain.BikePicture;
import com.thesis.bikerental.portfolio.bike.domain.BikePictureData;
import com.thesis.bikerental.portfolio.bike.service.BikeServiceImplementation;
import com.thesis.bikerental.utils.api.ApiSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/bike")
@RestController
public class BikeController {
    private final BikeServiceImplementation bikeServiceImplementation;

    @Autowired
    public BikeController(BikeServiceImplementation bikeServiceImplementation) {
        this.bikeServiceImplementation = bikeServiceImplementation;
    }

    @PostMapping
    public ResponseEntity<HashMap<String, ?>> createBike(@RequestBody Bike bike) throws CloneNotSupportedException {

        HashMap<String, Object> content =  new HashMap<>();

        bikeServiceImplementation.save(bike);

        content.put("data",bike);

        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, ?>> deleteBike(@RequestParam long id) {
        HashMap<String, ?> content =  new HashMap<>();
        bikeServiceImplementation.deleteById(id);

        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @PostMapping("/photo")
    public ResponseEntity<HashMap<String, ?>> deleteBike(@RequestBody MultipartFile photo) {
        HashMap<String, ?> content =  new HashMap<>();
        Bike bike = bikeServiceImplementation.findById(3);

        try {
            byte bikePhoto[]=photo.getBytes();
            BikePicture bikePicture = BikePicture.builder().bike(bike).image(bikePhoto).build();
            bike.getBikePictures().add(bikePicture);
            bikeServiceImplementation.save(bike);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @GetMapping("/photo")
    public ResponseEntity<HashMap<String, ?>> getBikePicture(@RequestParam ("id") long id) {
        HashMap<String, BikePictureData> content =  new HashMap<>();
        BikePictureData bikePicture = bikeServiceImplementation.getBikeImage(id);

        content.put("picture", bikePicture);

        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @PostMapping("/available")
    public List<Bike> getAvailableBike(@Argument String search, @Argument int page, @Argument int size, @Argument int status) {


        return bikeServiceImplementation.data(search,page,size,status);
    }

    @PostMapping("/rented")
    public List<Bike> getAvailableBikeRented(@Argument String search, @Argument int page, @Argument int size, @Argument int status, @Argument String token) {


        return bikeServiceImplementation.data(search,page,size,status);
    }

    @PostMapping("/requested")
    public List<Bike> getAvailableBikeRequested(@Argument String search, @Argument int page, @Argument int size, @Argument int status, @Argument String token) {


        return bikeServiceImplementation.data(search,page,size,status);
    }

    @GetMapping("/settings")
    public ResponseEntity<?> settings() {

        HashMap<String, Object> result = new HashMap<>();
        result.putIfAbsent("data", bikeServiceImplementation.apiSettings());


        return new ResponseEntity<>(result,HttpStatus.OK);
    }




    @SchemaMapping(typeName = "Query",value = "bikes")
        public List<Bike> getAllbike(@Argument String search, @Argument int page, @Argument int size, @Argument int status){
        return bikeServiceImplementation.data(search,page,size,status);
    }

    @SchemaMapping(typeName = "Query",value = "getBikeByCustomerRented")
    public List<Bike> getBikeRentedByCustomer(@Argument String search, @Argument int page, @Argument int size, @Argument String token){

        return bikeServiceImplementation.getBikeRentedByCustomer(search,page,size,token);
    }

    @SchemaMapping(typeName = "Query",value = "getBikeByCustomer")
    public List<Bike> getBikeRequestedByCustomer(@Argument String search, @Argument int page, @Argument int size, @Argument String token){

        return bikeServiceImplementation.getBikeRequestedByCustomer(search,page,size, token);
    }


    @SchemaMapping(typeName = "Query", value = "bikeById")
    public Bike bike(@Argument long id) {return bikeServiceImplementation.findById(id);}

}
