package com.openclassrooms.rentals.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MessageDto {
    @NotNull(message = "rental_id is mandatory")
    private Long rental_id;
    @NotBlank(message = "message is mandatory")
    private String message;

}
