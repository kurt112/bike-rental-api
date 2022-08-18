package com.thesis.bikerental.portfolio.bike.domain;
import com.thesis.bikerental.portfolio.store.domain.Store;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bike")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Bike implements Comparable<Bike>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "brand")
    private String brand;

    @Column(name = "size")
    private double size;

    @Column(name = "description")
    private String description;

    @Column(name = "available")
    private boolean available;

    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL)
    private List<BikePicture> bikePictures;

    @ManyToOne
    @JoinColumn(name = "assign_store")
    private Store store;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updated_at;

    @Override
    public int compareTo(Bike bike) {
        return (int)this.id - (int)bike.getId();
    }
}