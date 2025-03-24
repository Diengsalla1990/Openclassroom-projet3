package com.openclassrooms.rentals.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

import java.util.Date;

/**
* Représente un objet de transfert de données (DTO) pour 
* le transfert de données rental entre les couches.
* Cette classe permet d'encapsuler les informations 
* rentals envoyées ou reçues de l'API.
*/
@Data
public class RentalDto {
  
    private String name;
    private Double surface;
    private Double price;
    private MultipartFile picture;
    private String description;

    private Date created_at;

    private Date updated_at;

    
}
