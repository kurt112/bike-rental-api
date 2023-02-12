package com.thesis.bikerental.portfolio.bike.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.domain.BikePicture;
import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.portfolio.customer.service.CustomerRepository;
import com.thesis.bikerental.portfolio.notification.domain.Notification;
import com.thesis.bikerental.portfolio.notification.service.NotificationService;
import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.portfolio.user.service.UserRepository;
import com.thesis.bikerental.utils.Jwt;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.thesis.bikerental.portfolio.bike.domain.Bike.Status.NOT_AVAILABLE;
import static com.thesis.bikerental.portfolio.bike.domain.Bike.Status.RENTED;

@Transactional
@Service
@RequiredArgsConstructor
public class BikeServiceImplementation implements BikeService {

    private final BikeRepository bikeRepository;
    private final BikePictureRepository bikePictureRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    private final Jwt jwt;

    private ApiSettings apiSettings = new ApiSettings(0,0,0,0,0);

    //available
    @Override
    public List<Bike> data (String search, int page, int size, int status) {
        if(search.equals("all")){
            List<Bike> bikes = bikeRepository.getAllBikes(status);
            System.out.println("The status " + status);
            apiSettings.initApiSettings(bikes.size(),1,1,bikes.size());
            return bikes;
        }

        Pageable pageable = PageRequest.of(page-1,size);
        Page<Bike> pages;

        if(status == Bike.getBikeStatus(Bike.Status.NOT_RENTED)){
            pages = bikeRepository.getAllBikeAvailable(status,search,pageable);
        }else {
            pages = bikeRepository.getAllBike(status,search,pageable);
        }

        apiSettings.initApiSettings(size,page,pages.getTotalPages(),pages.getTotalElements());
        return pages.getContent();
    }

    @Override
    public Bike save(Bike bike) {
        try {
            bikeRepository.save(bike);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return bike;
    }

    @Override
    public boolean deleteById(long id) {

        Bike bike = bikeRepository.findById(id).orElse(null);

        if(bike  == null) return false;

        bike.setAvailable(false);

//        try {
//            bikeRepository.deleteById(bike.getId());
//        }catch (Exception e){
//            return false;
//        }

        return true;
    }

    @Override
    public Bike findById(long id) {
        return bikeRepository.findById(id).orElse(null);
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
    public List<Bike> getBikeByCustomer(String token) {
        String email = jwt.getUsername(token);
        User user = userRepository.findFirstByEmail(email);
        Customer customer =  user.getCustomer();
        return customer.getBikes();
    }

    @Override
    public Boolean rentBikeByCustomer(long userId, long bikeId) {

        User user = userRepository.findById(userId).orElse(null);

        if(user == null) return false;

        Bike bike = bikeRepository.findById(bikeId).orElse(null);

        if(bike == null) return false;


        Notification notification = Notification
                .builder()
                .to(bike.getAssignedCustomer().getUser())
                .from(null)
                .link("bike/rented?search="+user.getFirstName()+"&page=1&size=10&status=2")
                .message("Request bike is approve!")
                .build();
        notificationService.save(notification);

        Customer customer = user.getCustomer();

        customer.setNextBilled(bike.getStartBarrow());

        bike.setStatus(Bike.getBikeStatus(RENTED));
        bike.setDateCharge(bike.getStartBarrow());
        bike.setLatitude(bike.getParentBike().getLatitude());
        bike.setLongitude(bike.getParentBike().getLongitude());
        user.setRenting(true);
        customerRepository.saveAndFlush(customer);
        bikeRepository.saveAndFlush(bike);
        return true;
    }

    @Override
    public Boolean requestBikeByCustomer(String token, long bikeId, Bike customerBike, Date startBarrow, Date endBarrow) {
        String email = jwt.getUsername(token);

        if(email == null) return false;

        User user = userRepository.findFirstByEmail(email);

        if(user == null) return false;

        Bike bike = bikeRepository.findById(bikeId).orElse(null);

        if(bike == null) return false;
        if(bike.getQuantity() <=0) return false;

        Customer customer = user.getCustomer();
        long currentBikeQuantity = bike.getQuantity()-1;

        bike.setQuantity(currentBikeQuantity);

        // we will have a separate bike for customer
        customerBike.setId(0);
        customerBike.setStatus(Bike.getBikeStatus(Bike.Status.FOR_REQUEST));
        customerBike.setQuantity(1);
        customerBike.setAssignedCustomer(customer);
        customerBike.setStartBarrow(startBarrow);
        customerBike.setEndBarrow(endBarrow);
        customerBike.setParentBike(bike);
        customerBike.setBikePictures(null);
        customerBike.setAvailable(true);

        bikeRepository.save(bike);
        bikeRepository.save(customerBike);
        Notification notification = Notification
                .builder()
                .to(null)
                .from(user)
                .message("Requesting bike")
                .build();

        notificationService.save(notification);
        return true;
    }

    @Override
    public Boolean cancelRequestBikeByCustomer(String token, long bikeId) {
        String email = jwt.getUsername(token);

        if(email == null) return false;

        Bike bike = bikeRepository.findById(bikeId).orElse(null);

        if(bike == null) return false;

        if(bike.getAssignedCustomer() == null||!bike.getAssignedCustomer().getUser().getEmail().equals(email)){
            return false;
        }

        Notification notification = Notification
                .builder()
                .to(null)
                .from(bike.getAssignedCustomer().getUser())
                .message("Cancelled his request")
                .build();
        notificationService.save(notification);
        if(bike.getParentBike() == null){
            bikeRepository.delete(bike);
            return true;
        }

        Bike parentBike = bike.getParentBike();

        parentBike.setQuantity(bike.getQuantity()+1);
        bikeRepository.save(parentBike);
        bikeRepository.delete(bike);
        return true;
    }

    @Override
    public Boolean terminateRentedBikeByCustomer(long userId, long bikeId) {
        User user = userRepository.findById(userId).orElse(null);

        if(user == null) return false;

        Bike bike = bikeRepository.findById(bikeId).orElse(null);

        if(bike == null) return false;

        if(bike.getAssignedCustomer().getUser().getId() != user.getId()) return false;

        Notification notification = Notification
                .builder()
                .to(bike.getAssignedCustomer().getUser())
                .from(null)
                .message("Terminate your rent")
                .build();
        notificationService.save(notification);

        Bike parentBike = bike.getParentBike();

        long newQty = parentBike.getQuantity() + 1;
        parentBike.setQuantity(newQty);
        bike.setStatus(Bike.getBikeStatus(NOT_AVAILABLE));
        user.setRenting(false);


        userRepository.save(user);
        bikeRepository.save(bike);
        bikeRepository.save(parentBike);
        return true;
    }

    @Override
    public ResponseEntity<?> uploadBikePicture(String pictureName, long bikeId) {
        HashMap<String, String > result = new HashMap<>();

        Bike bike = findById(bikeId);

        if(bike == null){

            result.put("message", "No bike found");

            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }


        BikePicture bikePicture = BikePicture.builder().pictureName(pictureName).bike(bike).build();

        bikePictureRepository.save(bikePicture);


        result.put("message", "Uploaded Success");

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public Boolean rejectBikeRequest(long userId, long bikeId) {
        User user = userRepository.findById(userId).orElse(null);
        Bike bike = findById(bikeId);
        if(user == null) return false;

        if(bike == null) return false;

        if(bike.getParentBike() == null){
            bikeRepository.delete(bike);
            return true;
        }

        Notification notification = Notification
                .builder()
                .to(bike.getAssignedCustomer().getUser())
                .from(null)
                .message("Rejected your request")
                .build();
        notificationService.save(notification);
        Bike parentBike = bike.getParentBike();
        parentBike.setQuantity(bike.getQuantity()+1);
        bikeRepository.save(parentBike);
        bikeRepository.delete(bike);
        return true;
    }

    @Override
    public Boolean updateBikeLocationByUser(String token, String lng, String lat) {
        List<Bike> bikes = getBikeByCustomer(token);

        bikes.forEach(e -> {
            e.setLongitude(lng);
            e.setLatitude(lat);
            bikeRepository.save(e);
        });

        return true;
    }
}
