package com.thesis.bikerental.portfolio.customer.api;

import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.portfolio.customer.service.CustomerServiceImpl;
import com.thesis.bikerental.portfolio.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final UserServiceImpl userService;

    @PatchMapping
    public ResponseEntity<HashMap<String, ?>> updateCustomer(@RequestBody Customer customer){
        HashMap<String ,?> hashMap = new HashMap<>();

        customerService.save(customer);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, ?>> deleteCustomer(@RequestParam("id") long id){
        HashMap<String ,?> hashMap = new HashMap<>();

        customerService.deleteById(id);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HashMap<String, ?>> createCustomer(@RequestBody Customer customer){
        HashMap<String ,?> hashMap = new HashMap<>();
        if(customer.getUser() != null){
            customer.getUser().setPassword(new BCryptPasswordEncoder().encode(customer.getUser().getPassword()));
            userService.save(customer.getUser());
        }
        customerService.save(customer);
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @SchemaMapping(typeName = "Query",value = "customers")
    public List<Customer> getAllBike(@Argument String search, @Argument int page, @Argument int size, @Argument int status){

        return customerService.data(search,page,size,status);
    }


    @SchemaMapping(typeName = "Query",value = "customerById")
    public Customer getCustomerById(@Argument long id){

        return customerService.findById(id);
    }





}
