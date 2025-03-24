package com.openclassrooms.rentals.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class RegisterDto {
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "password is mandatory")
    private String password;

}
