package com.thesis.bikerental.portfolio.bike.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bike_picture")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class BikePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "bike", nullable = false)
    private Bike bike;

    @Lob
    @Column( name = "image", columnDefinition = "BLOB", nullable = true)
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;
}
