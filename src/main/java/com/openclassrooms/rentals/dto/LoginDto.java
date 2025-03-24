package com.openclassrooms.rentals.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "password is mandatory")
    private String password;

}
