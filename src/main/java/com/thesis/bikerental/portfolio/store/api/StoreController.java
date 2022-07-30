package com.thesis.bikerental.portfolio.store.api;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.store.domain.Store;
import com.thesis.bikerental.portfolio.store.service.StoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequestMapping("/store")
@RestController
public class StoreController {

    private final StoreServiceImpl storeService;

    @Autowired
    public StoreController(StoreServiceImpl storeService) {
        this.storeService = storeService;
    }

    @PutMapping
    public ResponseEntity<HashMap<String, ?>> updateStore(){
        HashMap<String ,?> hashMap = new HashMap<>();

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, ?>> deleteStore(){
        HashMap<String ,?> hashMap = new HashMap<>();

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HashMap<String, ?>> createStore(){
        HashMap<String ,?> hashMap = new HashMap<>();

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    // retrieving store
    @SchemaMapping(typeName = "Query",value = "stores")
    public List<Store> getAllStore(@Argument String search, @Argument int page, @Argument int size, @Argument int status){

        return storeService.data(search,page,size,status);
    }

    @SchemaMapping(typeName = "Query", value = "storeById")
    public Store store(@Argument long id) {return storeService.findById(id);}

}
