package com.openclassrooms.rentals.model;


import lombok.Data;
import javax.persistence.*;
import java.util.Date;


@Data
@Entity 
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long rental_id;
    @Column(nullable = false)
    private Long user_id;

    private String message;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;
}
