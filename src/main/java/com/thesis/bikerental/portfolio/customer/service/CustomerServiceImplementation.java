package com.thesis.bikerental.portfolio.customer.service;

import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.portfolio.notification.domain.Notification;
import com.thesis.bikerental.portfolio.notification.service.NotificationService;
import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.portfolio.user.service.UserRepository;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation implements CustomerService{

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private ApiSettings apiSettings = new ApiSettings(0,0,0,0,0);

    @Override
    public List<Customer> data(String search, int page, int size, int status) {
        Pageable pageable = PageRequest.of(page-1,size);

        Page<Customer> pages = customerRepository.getCustomers(search,pageable);

        apiSettings.initApiSettings(size,page,pages.getTotalPages(),pages.getTotalElements());

        return pages.getContent();
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        try {
            customerRepository.save(customer);
            Notification notification = Notification
                    .builder()
                    .to(null)
                    .from(null)
                    .link("")
                    .message(customer.getUser().getFirstName() + " " + customer.getUser().getLastName() + " is our new customer!")
                    .build();
            notificationService.save(notification);
        }catch (Exception e){
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        Customer customer = findById(id);

        if(customer == null) return false;

        customerRepository.deleteById(id);
        Notification notification = Notification
                .builder()
                .to(null)
                .from(null)
                .link("")
                .message(customer.getUser().getFirstName() + " " + customer.getUser().getLastName() + " is deleted in our system")
                .build();
        notificationService.save(notification);
        return true;
    }

    @Override
    public Customer findById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    @Override
    public ApiSettings apiSettings() {
        return apiSettings;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public ResponseEntity<?> getUserBill(Long userId) {
        HashMap<String , Object> result = new HashMap<>();

        User user = userRepository.findById(userId).orElse(null);

        if(user == null) {
            result.put("data", "User Not Found");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        result.put("data", user.getCustomer().getToPay());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
