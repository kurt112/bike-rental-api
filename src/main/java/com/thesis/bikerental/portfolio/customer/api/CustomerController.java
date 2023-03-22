package com.thesis.bikerental.portfolio.customer.api;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.service.BikeService;
import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.portfolio.customer.domain.CustomerReceipt;
import com.thesis.bikerental.portfolio.customer.service.CustomerReceiptService;
import com.thesis.bikerental.portfolio.customer.service.CustomerService;
import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.portfolio.user.service.UserService;
import com.thesis.bikerental.utils.Jwt;
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

    private final CustomerService customerService;
    private final UserService userService;
    private final BikeService bikeService;
    private final CustomerReceiptService customerReceiptService;
    private final Jwt jwt;

    @PatchMapping
    public ResponseEntity<HashMap<String, ?>> updateCustomer(@RequestBody Customer customer){
        HashMap<String ,Object> hashMap = new HashMap<>();

        User user = customer.getUser();

        if(user == null){
            hashMap.put("message","Customer user does not exist");
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }

        System.out.println(user.isAccountNotExpired());
        System.out.println(user.isEnabled());

        userService.validateUser(hashMap, user);

        if(hashMap.size() > 0) return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);

        customerService.save(customer);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, ?>> deleteCustomer(@RequestParam("id") long id){
        HashMap<String ,?> hashMap = new HashMap<>();

        customerService.deleteById(id);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }
    @PostMapping("/payment")
    public ResponseEntity<?>  customerPayment(@RequestParam("email") String email, @RequestParam("payment") double payment, @RequestParam("token") String token) {
        return customerService.customerPay(email,payment, token);
    }

    @PostMapping
    public ResponseEntity<HashMap<String, ?>> createCustomer(@RequestBody Customer customer){
        HashMap<String ,Object> hashMap = new HashMap<>();

        User user = customer.getUser();

        if(user == null){
            hashMap.put("message","Customer user does not exist");
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }

        userService.validateUser(hashMap, user);

        if(hashMap.size() > 0) return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);


        if(customer.getUser() != null){
            customer.getUser().setPassword(new BCryptPasswordEncoder().encode(customer.getUser().getPassword()));
            user.setEnabled(true);
            user.setCredentialNotExpired(true);
            user.setAccountNotExpired(true);
            user.setAccountNotLocked(true);
            userService.save(customer.getUser());
        }

        customer.setActive(true);
        customerService.save(customer);
        hashMap.put("userId", user.getId());
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @GetMapping("/settings")
    public ResponseEntity<?> settings() {

        HashMap<String, Object> result = new HashMap<>();
        result.putIfAbsent("data", customerService.apiSettings());


        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/receipt/settings")
    public ResponseEntity<?> receiptSettings() {

        HashMap<String, Object> result = new HashMap<>();
        result.putIfAbsent("data", customerReceiptService.apiSettings());


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

        return customerService.getUserBill(id);
    }

    @GetMapping("/{token}/isRenting")
    public ResponseEntity<?> isUserRenting(@PathVariable("token") String token){

        return userService.isUserRenting(token);
    }

    @PostMapping("/receipt")
    public ResponseEntity<?> uploadReceipt(@RequestParam("picture") String picturePath,
                                           @RequestParam("token") String token){
        HashMap<String ,Object> result = new HashMap<>();
        String email = jwt.getUsername(token);

        User user = userService.findByEmail(email);
        if(user == null) {
            result.put("data", "User can't find");
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        CustomerReceipt receipt = CustomerReceipt
                .builder()
                .customer(user.getCustomer())
                .bike(null)
                .picture(picturePath)
                .build();

        customerReceiptService.save(receipt);

        result.put("data", "Bike Receipt Success");

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @SchemaMapping(typeName = "Query",value = "customers")
    public List<Customer> getAllBike(@Argument String search, @Argument int page, @Argument int size, @Argument int status){

        return customerService.data(search,page,size,status);
    }

    @SchemaMapping(typeName = "Query",value = "getCustomerReceipts")
    public List<CustomerReceipt> getCustomerReceipts(@Argument String search, @Argument int page, @Argument int size, @Argument int status){

        return customerReceiptService.data(search,page,size,status);
    }

    @SchemaMapping(typeName = "Query",value = "customerById")
    public Customer getCustomerById(@Argument long id){

        return customerService.findById(id);
    }
}
