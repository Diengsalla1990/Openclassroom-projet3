package com.openclassrooms.rentals.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;



@Data
@Entity 
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;

    private String name;
    @Column(nullable = false)
    private String password;

    @Column
    private Date created_at;

    @Column
    private Date updated_at;
}
