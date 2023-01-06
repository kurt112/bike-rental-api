package com.thesis.bikerental.portfolio.customer.api;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.service.BikeService;
import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.portfolio.customer.service.CustomerService;
import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.portfolio.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final UserServiceImpl userService;
    private final BikeService bikeService;

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
        HashMap<String ,Object> hashMap = new HashMap<>();

        User user = customer.getUser();

        if(user == null){
            hashMap.put("message","Customer user does not exist");
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }

        if(userService.findByEmail(user.getEmail()) !=null){
            hashMap.put("email","Email already exist");
        }

        if(userService.findByCellphone(user.getCellphone()) !=null){
            hashMap.put("cellphone","Cellphone number must be unique");
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }

        if(customer.getUser() != null){
            customer.getUser().setPassword(new BCryptPasswordEncoder().encode(customer.getUser().getPassword()));
            userService.save(customer.getUser());
        }

        customer.setActive(true);
        customerService.save(customer);
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @GetMapping("/settings")
    public ResponseEntity<?> settings() {

        HashMap<String, Object> result = new HashMap<>();
        result.putIfAbsent("data", customerService.apiSettings());


        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/rented")
    public List<Bike> getAllBikeRentedByCustomer(@Argument String search, @Argument int page, @Argument int size, @Argument int status, @Argument String token) {


        return bikeService.data(search,page,size,status);
    }

    @PostMapping("/requested")
    public List<Bike> getAllBikeRequested(@Argument String search, @Argument int page, @Argument int size, @Argument int status, @Argument String token) {


        return bikeService.data(search,page,size,status);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getBill(@PathVariable("userId") Long id){

        System.out.println("The id "+  id);

        return customerService.getUserBill(id);
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
