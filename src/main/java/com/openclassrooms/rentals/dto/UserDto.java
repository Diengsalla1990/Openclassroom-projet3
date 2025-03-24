package com.openclassrooms.rentals.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import java.util.Date;

/**
* Représente un objet de transfert de données (DTO) pour 
* le transfert de données utilisateur entre les couches.
* Cette classe permet d'encapsuler les informations 
* utilisateur envoyées ou reçues de l'API.
*/
@Data
public class UserDto {
    private Long id;
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "password is mandatory")
    private String password;
    private Date created_at;
    private Date updated_at;

}
