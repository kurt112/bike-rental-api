package com.thesis.bikerental.portfolio.bike.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bike_picture")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BikePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "bike", nullable = false)
    private Bike bike;

    @Column(name = "picture_name")
    private String pictureName;
}
