package com.thesis.bikerental.portfolio.bike.api;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.service.BikeService;
import com.thesis.bikerental.portfolio.store.domain.Store;
import com.thesis.bikerental.portfolio.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/bike")
@RestController
@RequiredArgsConstructor
public class BikeController {
    private final BikeService bikeService;
    private final StoreService storeService;

    @PostMapping("{store-id}")
    public ResponseEntity<HashMap<String, ?>> createBike(@PathVariable("store-id") long storeId, @RequestBody Bike bike) {

        HashMap<String, Object> content =  new HashMap<>();

        Store store = storeService.findById(storeId);

        if(store ==null) {
            content.put("data", "No Store Found");
            return new ResponseEntity<>(content,HttpStatus.BAD_REQUEST);
        }

        bike.setStore(store);
        bikeService.save(bike);

        content.put("data",bike);

        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, ?>> deleteBike(@RequestParam long id) {
        HashMap<String, String> content =  new HashMap<>();

        if(bikeService.deleteById(id)){
            content.put("message", "Bike delete successful");

            return new ResponseEntity<>(content,HttpStatus.OK);
        }

        content.put("message", "This item is using to other component");
        return new ResponseEntity<>(content,HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}/photo/{name}")
    public ResponseEntity<?> uploadBikePicture(@PathVariable("name") String bikePicture,@PathVariable("id") long id) {


        return bikeService.uploadBikePicture(bikePicture,id);
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

    @PostMapping("/request/{token}/{bikeId}/{start}/{end}")
    public ResponseEntity<?>  customerRequestBike(@PathVariable("token") String token,
                                                  @PathVariable("bikeId") long bikeId,
                                                  @PathVariable("start") Date start,
                                                  @PathVariable("end") Date end,
                                                  @RequestBody Bike bike) {
        HashMap<String, Object> result = new HashMap<>();

        if(!bikeService.requestBikeByCustomer(token, bikeId,bike, start,end)){
            result.putIfAbsent("data", "bike request invalid");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.putIfAbsent("data", "bike request success");
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?>  customerCancelBikeRequest(@RequestParam("token") String token, @RequestParam("bikeId") long bikeId) {
        HashMap<String, Object> result = new HashMap<>();

        if(!bikeService.cancelRequestBikeByCustomer(token, bikeId)){
            result.putIfAbsent("data", "bike request invalid");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.putIfAbsent("data", "bike request success");
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @PostMapping("/request/approval")
    public ResponseEntity<?> approveRequest(@RequestParam("userId") long userId, @RequestParam("bikeId") long bikeId){
        HashMap<String, Object> result = new HashMap<>();

       if(! bikeService.rentBikeByCustomer(userId,bikeId)){
           result.putIfAbsent("data", "bike approval invalid");
           return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
       }


        result.putIfAbsent("data", "bike approved success");
        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }

    @PostMapping("/request/rejected")
    public ResponseEntity<?> rejectRequest(@RequestParam("userId") long userId, @RequestParam("bikeId") long bikeId){
        HashMap<String, Object> result = new HashMap<>();
        System.out.println("I am in request rejected");
        if(!bikeService.rejectBikeRequest(userId,bikeId)){
            result.putIfAbsent("data", "bike reject invalid");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }


        result.putIfAbsent("data", "Bike reject success");
        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }


    @PostMapping("/terminate")
    public ResponseEntity<?> terminateBikeRent(@RequestParam("userId") long userId, @RequestParam("bikeId") long bikeId){
        HashMap<String, Object> result = new HashMap<>();

        if(! bikeService.terminateRentedBikeByCustomer(userId,bikeId)){
            result.putIfAbsent("data", "bike terminate invalid");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }


        result.putIfAbsent("data", "bike terminate success");
        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }

    @PostMapping("/update/location")
    public ResponseEntity<?> updateLocation(@RequestParam("lat") String latitude,
                                            @RequestParam("lng") String longitude,
                                            @RequestParam("token") String token){

        HashMap<String, Object> result = new HashMap<>();
        bikeService.updateBikeLocationByUser(token,longitude,latitude);
//        System.out.println("updating location");
        result.put("data", "bike location updated");
        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }



    @SchemaMapping(typeName = "Query",value = "bikes")
        public List<Bike> getAllBike(@Argument String search, @Argument int page, @Argument int size, @Argument int status){
        return bikeService.data(search,page,size,status);
    }


    @SchemaMapping(typeName = "Query",value = "getBikeByCustomer")
    public List<Bike> getBikeByCustomer(@Argument String search, @Argument String token){


        return bikeService.getBikeByCustomer(token);
    }


    @SchemaMapping(typeName = "Query", value = "bikeById")
    public Bike bike(@Argument long id) {return bikeService.findById(id);}

}
