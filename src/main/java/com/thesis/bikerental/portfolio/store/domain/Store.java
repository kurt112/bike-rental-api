package com.thesis.bikerental.portfolio.store.domain;

import com.thesis.bikerental.portfolio.employee.domain.Employee;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "store")
public class Store implements Comparable<Store> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "radius")
    private int radius;

    @Column(name = "scope_color")
    private String scopeColor;

    @Column(name = "scope_edge_color")
    private String scopeEdgeColor;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "parent_store_id")
    private Store parentStore;

    @OneToMany(mappedBy = "parentStore")
    private Set<Store> subStore;

    @OneToMany(mappedBy = "currentAssign")
    private Set<Employee> employees;

    private String paymaya;
    private String gcash;
    private String bdo;
    private String bpi;
    private String securityBank;

//    @OneToMany(mappedBy = "store")
//    private Set<Bike> bikes;


    @Override
    public int compareTo(Store store) {
        return (int)(this.id - store.id);
    }
}
