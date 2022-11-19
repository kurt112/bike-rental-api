package com.thesis.bikerental.infrastructure.scheduler;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.service.BikeRepository;
import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.portfolio.customer.service.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final CustomerRepository customerRepository;
    private final BikeRepository bikeRepository;



    @Scheduled(fixedRate = 15_000) // 5 minutes  will re-run this and billed the customer
    public void chargeCustomerBike() {
        List<Bike> bikeList = bikeRepository.getBikeWithCustomer();

        for(Bike bike: bikeList){
            Customer customer = bike.getAssignedCustomer();
            /**
             * since the price of the bike is billed by per hour we will divide it by four
             * since our task will run every 15 minutes
             */
            double bikePricePerFifteenMinutes = (bike.getPrice() / 4);
            double currentBill = customer.getToPay();

            // for comparing the bill
            Calendar currentTime = Calendar.getInstance();


            Calendar customerNextBill = Calendar.getInstance();
            customerNextBill.setTime(customer.getNextBilled());

            if(currentTime.compareTo(customerNextBill) > 0){

                System.out.println("===================================================================================");
                System.out.println("Charging Customer");
                System.out.println("===================================================================================");

                while (currentTime.compareTo(customerNextBill) >=0) {
                    currentBill += bikePricePerFifteenMinutes;
                    customerNextBill.add(Calendar.MINUTE, 15);
                }

                customer.setNextBilled(customerNextBill.getTime());
                customerNextBill.add(Calendar.MINUTE, -15);
                customer.setLastBilled(customerNextBill.getTime());
                customer.setToPay(currentBill);
                customerRepository.saveAndFlush(customer);
            }

        }
    }
}
