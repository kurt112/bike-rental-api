package com.thesis.bikerental.portfolio.store.domain;

import com.thesis.bikerental.portfolio.bike.domain.Bike;
import com.thesis.bikerental.portfolio.employee.domain.Employee;
import lombok.*;

import javax.persistence.*;
import java.util.List;
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "parent_store_id")
    private Store parentStore;

    @OneToMany(mappedBy = "parentStore")
    private Set<Store> subStore;

    @OneToMany(mappedBy = "currentAssign")
    private Set<Employee> employees;

    @OneToMany(mappedBy = "store")
    private Set<Bike> bikes;


    @Override
    public int compareTo(Store store) {
        return this.id - store.id;
    }
}
