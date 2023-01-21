package com.thesis.bikerental.portfolio.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thesis.bikerental.portfolio.customer.domain.Customer;
import com.thesis.bikerental.portfolio.employee.domain.Employee;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public  class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;
    @Column(name= "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name="user_password")
    private String password;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "role")
    private String userRole;

    @Column(name = "cellphone", unique = true)
    private String cellphone;

    @JsonProperty("isAccountNotExpired")
    @Column(name = "is_account_not_expired")
    private boolean isAccountNotExpired;

    @JsonProperty("isAccountNotLocked")
    @Column(name = "is_account_not_locked")
    private boolean isAccountNotLocked;

    @JsonProperty("isCredentialNotExpired")
    @Column(name = "is_credential_not_expired")
    private boolean isCredentialNotExpired;

    @JsonProperty("isEnabled")
    @Column(name ="is_enable")
    private boolean isEnabled;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToOne(mappedBy = "user")
    private Customer customer;

    @OneToOne(mappedBy = "user")
    private Employee employee;

    @Override
    public int compareTo(User o) {

        return o.getLastName().compareToIgnoreCase(lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User tempUser = (User) o;
        return this.id == tempUser.id;
    }
}