package com.openclassrooms.rentals.model;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double surface;

    private Double price;

    private String picture;

    private String description;
    @Column(nullable = false)
    private Long owner_id;

    private Date created_at;

    private Date updated_at;
}
