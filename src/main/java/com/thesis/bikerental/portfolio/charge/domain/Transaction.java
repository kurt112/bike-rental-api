package com.thesis.bikerental.portfolio.charge.domain;


import com.thesis.bikerental.portfolio.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "store_transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "transact_by")
    private User chargedTo;

    private double amount;

    private double pastAmount;

    private double newAmount;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", updatable = false)
    private Date date;

}
