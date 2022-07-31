package com.thesis.bikerental.portfolio.bike.api;


import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.service.BikeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<HashMap<String, ?>> createBike(@RequestBody Bike bike) {
        HashMap<String, ?> content =  new HashMap<>();
        bikeServiceImplementation.save(bike);

        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, ?>> deleteBike(@RequestParam long id) {
        HashMap<String, ?> content =  new HashMap<>();
        bikeServiceImplementation.deleteById(id);

        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @SchemaMapping(typeName = "Query",value = "bikes")
    public List<Bike> getAllbike(@Argument String search, @Argument int page, @Argument int size, @Argument int status){

        return bikeServiceImplementation.data(search,page,size,status);
    }


    @SchemaMapping(typeName = "Query", value = "bikeById")
    public Bike bike(@Argument long id) {return bikeServiceImplementation.findById(id);}

}
