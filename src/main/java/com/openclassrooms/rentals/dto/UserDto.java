package com.openclassrooms.rentals.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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
