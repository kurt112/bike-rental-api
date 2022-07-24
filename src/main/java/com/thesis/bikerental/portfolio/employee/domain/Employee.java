package com.thesis.bikerental.portfolio.employee.domain;

import com.thesis.bikerental.portfolio.store.domain.Store;
import com.thesis.bikerental.portfolio.user.domain.User;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "employee")
@Entity
public class Employee implements Comparable<Employee>{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JoinColumn(name = "store_id")
    @ManyToOne()
    private Store currentAssign;

    @Override
    public int compareTo(Employee employee) {
        return this.id - employee.id;
    }
}
