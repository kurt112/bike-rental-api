package com.thesis.bikerental.infrastructure.scheduler;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.bike.service.BikeRepository;
import com.thesis.bikerental.portfolio.charge.domain.Transaction;
import com.thesis.bikerental.portfolio.charge.service.TransactionRepository;
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
    private final TransactionRepository transactionRepository;



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


            // get the target billed for current bike
            Calendar bikeTargetCharge = Calendar.getInstance();
            bikeTargetCharge.setTime(bike.getDateCharge());


            Calendar customerTargetNextBilled = Calendar.getInstance();
            customerTargetNextBilled.setTime(customer.getNextBilled());
            // if the current bike charge is less than customer next billed
            // we will set the less bike charge as customer next billed
            if(bikeTargetCharge.compareTo(customerTargetNextBilled) > 0) {
                customer.setNextBilled(bikeTargetCharge.getTime());
                customerRepository.saveAndFlush(customer);
            }

            if(currentTime.compareTo(bikeTargetCharge) > 0){

                while (currentTime.compareTo(bikeTargetCharge) >=0) {
                    Transaction transaction = Transaction
                            .builder()
                            .amount(bikePricePerFifteenMinutes)
                            .newAmount(currentBill +bikePricePerFifteenMinutes)
                            .pastAmount(currentBill)
                            .chargedTo(customer.getUser())
                            .description("Added charged with the amount of " + bikePricePerFifteenMinutes +
                                    " for the renting bike with the code of " + bike.getCode())
                            .date(bikeTargetCharge.getTime())
                            .build();
                    currentBill += bikePricePerFifteenMinutes;
                    System.out.println("charging customer");
                    transactionRepository.saveAndFlush(transaction);
                    bikeTargetCharge.add(Calendar.MINUTE, 15);
                }


                bike.setDateCharge(bikeTargetCharge.getTime());
                bikeRepository.saveAndFlush(bike);

                customer.setToPay(currentBill);
                customer.setLastBilled(bikeTargetCharge.getTime());
                customerRepository.saveAndFlush(customer);
            }

        }
    }
}
