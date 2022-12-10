package com.thesis.bikerental.portfolio.bike.api;


import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.domain.BikePicture;
import com.thesis.bikerental.portfolio.bike.domain.BikePictureData;
import com.thesis.bikerental.portfolio.bike.service.BikeService;
import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.portfolio.user.service.UserService;
import com.thesis.bikerental.utils.Jwt;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BikeController {
    private final BikeService bikeService;
    private final UserService userService;

    private final Jwt jwt;


    @PostMapping
    public ResponseEntity<HashMap<String, ?>> createBike(@RequestBody Bike bike) {

        HashMap<String, Object> content =  new HashMap<>();

        bikeService.save(bike);

        content.put("data",bike);

        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, ?>> deleteBike(@RequestParam long id) {
        HashMap<String, ?> content =  new HashMap<>();
        bikeService.deleteById(id);

        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @PostMapping("/photo")
    public ResponseEntity<HashMap<String, ?>> uploadBikePicture(@RequestBody MultipartFile photo, @RequestParam("bike-id") long id) {
        System.out.println("The bike ID ");
        System.out.println(id);
        HashMap<String, ?> content =  new HashMap<>();
        Bike bike = bikeService.findById(id);

        try {
            byte bikePhoto[]=photo.getBytes();
            BikePicture bikePicture = BikePicture.builder().bike(bike).image(bikePhoto).build();
            bike.getBikePictures().add(bikePicture);
            bikeService.save(bike);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @GetMapping("/photo")
    public ResponseEntity<HashMap<String, ?>> getBikePicture(@RequestParam ("id") long id) {
        HashMap<String, BikePictureData> content =  new HashMap<>();
        BikePictureData bikePicture = bikeService.getBikeImage(id);

        content.put("picture", bikePicture);

        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @PostMapping("/available")
    public List<Bike> getAvailableBike(@Argument String search, @Argument int page, @Argument int size, @Argument int status) {


        return bikeService.data(search,page,size,status);
    }

    @PostMapping("/rented")
    public List<Bike> getAllBikeRentedByCustomer(@Argument String search, @Argument int page, @Argument int size, @Argument int status, @Argument String token) {


        return bikeService.data(search,page,size,status);
    }

    @PostMapping("/requested")
    public List<Bike> getAllBikeRequested(@Argument String search, @Argument int page, @Argument int size, @Argument int status, @Argument String token) {


        return bikeService.data(search,page,size,status);
    }

    @GetMapping("/settings")
    public ResponseEntity<?> settings() {

        HashMap<String, Object> result = new HashMap<>();
        result.putIfAbsent("data", bikeService.apiSettings());


        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/request/approved")
    public ResponseEntity<?> approveRequest(@RequestParam("userId") long userId, @RequestParam("bikeId") long bikeId){
        HashMap<String, Object> result = new HashMap<>();

        User user = userService.findById(userId);

        System.out.println("the user ");

        if(user == null){
            result.put("message","No user Found");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        Bike bike = bikeService.findById(bikeId);
        if(bike == null){
            result.put("message", "No bike Found");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }



        bike.setStatus(Bike.getBikeStatus(Bike.Status.RENTED));
        bike.setAssignedCustomer(user.getCustomer());

        bikeService.save(bike);
        result.put("message", "Bike request success");

        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }




    @SchemaMapping(typeName = "Query",value = "bikes")
        public List<Bike> getAllBike(@Argument String search, @Argument int page, @Argument int size, @Argument int status){
        return bikeService.data(search,page,size,status);
    }


    @SchemaMapping(typeName = "Query",value = "getBikeByCustomer")
    public List<Bike> getBikeByCustomer(@Argument String search, @Argument String token){


        return bikeService.getBikeByCustomer(search,token);
    }


    @SchemaMapping(typeName = "Query", value = "bikeById")
    public Bike bike(@Argument long id) {return bikeService.findById(id);}

}
