package com.thesis.bikerental.portfolio.customer.domain;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer_receipt")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CustomerReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @Column(name = "picture")
    private String picture;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
}
