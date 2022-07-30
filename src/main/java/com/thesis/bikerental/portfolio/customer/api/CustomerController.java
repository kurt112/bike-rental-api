package com.thesis.bikerental.portfolio.customer.api;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.portfolio.customer.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceImpl service;

    @Autowired
    public CustomerController(CustomerServiceImpl service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<HashMap<String, ?>> updateCustomer(@RequestBody Customer customer){
        HashMap<String ,?> hashMap = new HashMap<>();

        service.save(customer);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, ?>> deleteCustomer(@RequestParam("id") long id){
        HashMap<String ,?> hashMap = new HashMap<>();
        service.deleteById(id);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HashMap<String, ?>> createCustomer(@RequestBody Customer customer){
        HashMap<String ,?> hashMap = new HashMap<>();
        service.save(customer);
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @SchemaMapping(typeName = "Query",value = "customers")
    public List<Customer> getAllbike(@Argument String search, @Argument int page, @Argument int size, @Argument int status){

        return service.data(search,page,size,status);
    }


    @SchemaMapping(typeName = "Query",value = "customerById")
    public Customer getCustomerById(@Argument long id){

        return service.findById(id);
    }





}
