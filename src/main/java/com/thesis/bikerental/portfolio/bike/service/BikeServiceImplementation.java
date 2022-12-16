package com.thesis.bikerental.portfolio.bike.service;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.domain.BikePicture;
import com.thesis.bikerental.portfolio.bike.domain.BikePictureData;
import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.portfolio.customer.service.CustomerRepository;
import com.thesis.bikerental.portfolio.user.domain.User;
import com.thesis.bikerental.portfolio.user.service.UserRepository;
import com.thesis.bikerental.utils.Jwt;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    private final Jwt jwt;

    private ApiSettings apiSettings = new ApiSettings(0,0,0,0,0);


    //available
    @Override
    public List<Bike> data (String search, int page, int size, int status) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Bike> pages = null;


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

        Optional<Bike> bike = bikeRepository.findById(id);

        if(bike.isEmpty()) return false;

        bikeRepository.deleteById(bike.get().getId());

        return true;
    }

    @Override
    public Bike findById(long id) {
        Optional<Bike> bike = bikeRepository.findById(id);
        if(!bike.isEmpty()){
//            System.out.println(BlobProxy.generateProxy(bike.get().getBikePictures().get(0).getImage()).toString());
        }
        return bike.orElse(null);
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
    public BikePictureData getBikeImage(long id) {
        Optional<BikePicture> bikePicture = bikePictureRepository.findById(id);

        if(bikePicture.isEmpty()) return null;
        BikePicture picture = bikePicture.get();

        return new BikePictureData(picture.getId(),Base64Utils.encodeToString(picture.getImage()));
    }


    @Override
    public List<Bike> getBikeByCustomer(String search, String token) {

        String email = jwt.getUsername(token);

        User user = userRepository.findByEmail(email);

        Customer customer =  user.getCustomer();


        return customer.getBikes();
    }

    @Override
    public Boolean rentBikeByCustomer(long userId, long bikeId) {

        User user = userRepository.findById(userId).orElse(null);

        if(user == null) return false;

        Bike bike = bikeRepository.findById(bikeId).orElse(null);

        if(bike == null) return false;

        Customer customer = user.getCustomer();

        customer.setNextBilled(bike.getStartBarrow());

        bike.setStatus(Bike.getBikeStatus(RENTED));
        customerRepository.saveAndFlush(customer);
        bikeRepository.save(bike);
        return true;
    }

    @Override
    public Boolean requestBikeByCustomer(String token, long bikeId, Bike customerBike, Date startBarrow, Date endBarrow) {
        String email = jwt.getUsername(token);

        if(email == null) return false;

        User user = userRepository.findByEmail(email);

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

        bikeRepository.save(bike);
        bikeRepository.save(customerBike);

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

        Bike parentBike = bike.getParentBike();

        long newQty = parentBike.getQuantity() + 1;
        parentBike.setQuantity(newQty);
        bike.setStatus(Bike.getBikeStatus(NOT_AVAILABLE));


        bikeRepository.save(bike);
        bikeRepository.save(parentBike);
        return true;
    }
}
