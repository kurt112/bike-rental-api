package com.thesis.bikerental.portfolio.bike.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.portfolio.customer.domain.CustomerReceipt;
import com.thesis.bikerental.portfolio.store.domain.Store;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bike")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Bike implements Comparable<Bike>, Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code", nullable = false)
    private String code;

    private String name;

    private double price;

    private String brand;

    private double size;

    private String description;

    private long quantity;

    private int status;

    @JsonProperty("isAvailable")
    @Column(name = "available")
    private boolean isAvailable;

    private String longitude;

    private String latitude;

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BikePicture> bikePictures;

    @ManyToOne
    @JoinColumn(name = "assign_store")
    private Store store;

    // will null if the customer is blank
    @ManyToOne
    @JoinColumn(name = "assigned_customer")
    private Customer assignedCustomer;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updated_at;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_barrow")
    private Date startBarrow;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_barrow")
    private Date endBarrow;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_bike")
    private Bike parentBike;

    @Column(name = "date_charge")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCharge;

    @OneToOne(mappedBy = "bike", cascade = CascadeType.ALL)
    private CustomerReceipt customerReceipt;
    @Override
    public int compareTo(Bike bike) {
        return (int)this.id - (int)bike.getId();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static int getBikeStatus(Status status){
         switch (status){
             case NOT_RENTED: return 0;
             case FOR_REQUEST: return 1;
             case RENTED: return 2;
             case NOT_AVAILABLE: return -1;
        }

        return -2;
    }

    /**
     * 0 = not rented
     * 1 = for request
     * 2 = rented
     * -1 = not available
     */
    public enum Status {
        NOT_RENTED,
        FOR_REQUEST,
        RENTED,
        NOT_AVAILABLE
    }

}