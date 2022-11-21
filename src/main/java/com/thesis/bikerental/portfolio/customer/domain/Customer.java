package com.thesis.bikerental.portfolio.customer.domain;

import com.thesis.bikerental.portfolio.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(name = "to_pay", columnDefinition="Decimal(10,2)")
    private double toPay;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "last_billed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastBilled;

    @Column(name = "next_billed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextBilled;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
}
