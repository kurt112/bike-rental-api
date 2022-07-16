package com.thesis.bikerental.portfolio.user.domain;



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
public  class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "picture",columnDefinition = "TEXT")
    private String picture;
    @Column(name= "last_name")
    private String lastName;

    @Column(name = "sufix")
    private String suffix;

    @Column(name = "gender")
    private String gender;

    @Column(name="user_password")
    private String password;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "role")
    private String userRole;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "is_account_not_expired")
    private boolean isAccountNotExpired;

    @Column(name = "is_account_not_locked")
    private boolean isAccountNotLocked;

    @Column(name = "is_credential_not_expired")
    private boolean isCredentialNotExpired;

    @Column(name ="is_enable")
    private boolean isEnabled;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updated_at;

    @Override
    public int compareTo(User o) {

        return o.getLastName().compareToIgnoreCase(lastName);
    }


}