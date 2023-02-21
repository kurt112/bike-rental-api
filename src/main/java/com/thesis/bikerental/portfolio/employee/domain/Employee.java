
package com.thesis.bikerental.portfolio.employee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@ToString
public class Employee implements Comparable<Employee>{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @JoinColumn(name = "store_id")
    @ManyToOne()
    private Store currentAssign;

    @JsonProperty("isActive")
    @Column(name = "is_active")
    private boolean isActive;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee tempEmployee = (Employee) o;
        return this.id == tempEmployee.id;
    }

    @Override
    public int compareTo(Employee employee) {
        return (int)(this.id - employee.id);
    }
}
