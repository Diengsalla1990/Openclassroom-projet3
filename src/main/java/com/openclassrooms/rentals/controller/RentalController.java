package com.openclassrooms.rentals.controller;


import com.openclassrooms.rentals.dto.GenericMessageDto;
import com.openclassrooms.rentals.dto.RentalDto;
import com.openclassrooms.rentals.dto.RentalsListDto;
import com.openclassrooms.rentals.exceptions.InvalidUserException;
import com.openclassrooms.rentals.model.Rental;
import com.openclassrooms.rentals.service.DocumentStorageService;
import com.openclassrooms.rentals.service.RentalService;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;


/**
* Contrôleur REST pour la gestion des requêtes HTTP liées aux Rentals
* Cette classe expose les points de terminaison pour les opérations Rentals
* telles que la récupération, la création et la mise à jour
*/
@RestController
public class RentalController {
    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DocumentStorageService documentStorageService;
    
    @Autowired
    private ModelMapper modelMapper;
    
     
    /**
     * Récupère toutes les locations.
     *
     * @return ResponseEntity<Object> contenant une liste de RentalsDTO représentant toutes les locations
     */
    
   
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Rental.class),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Interdit"),
            @ApiResponse(code = 404, message = "pas trouvé"),
            @ApiResponse(code = 500, message = "Une erreur s'est produite")
    })
    @ApiOperation(value = "Get all rentals", notes= "Return all rentals", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping("/api/rentals")
    public RentalsListDto getAllRentals() {
        RentalsListDto rentalsListDto = new RentalsListDto();
        ArrayList<Rental> rentalList = new ArrayList<>();
        rentalService.getAll().forEach(rentalList::add);
        rentalsListDto.setRentals(rentalList);
        return rentalsListDto; 
    }
    
    
    
    /**
     * Récupère une location spécifique en fonction de l'ID fourni.
     * @param id
     * @return la location grace a son ID
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Rental.class),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Interdit"),
            @ApiResponse(code = 404, message = "pas trouvé"),
            @ApiResponse(code = 500, message = "Une erreur s'est produite")
    })

    @ApiOperation(value = "Get rental by Id", notes ="Return rental by Id", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping("/api/rentals/{id}")
    public Rental getRentalById(@PathVariable("id") Long id) {

        return rentalService.getRental(id);
    }
    
    /**
     * Ajoute une nouvelle location en fonction des données de demande fournies.
     * @param token Le jeton d’authentification obtenu à partir de l’en-tête de la demande.
     * @param rentalDto
     * @return ResponseEntity<Object> 
     * @throws IOException
     */
    
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rental created", response = Rental.class),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Interdit"),
            @ApiResponse(code = 404, message = "pas trouvé"),
            @ApiResponse(code = 500, message = "Une erreur s'est produite")
    })
    
    @ApiOperation(value = "Create rental", notes ="Return rental by Id", produces = "application/json")
    @PostMapping(path = "/api/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericMessageDto> createRental(@RequestHeader("Authorization") String token, @Valid @ModelAttribute RentalDto rentalDto) throws IOException {
        String pathAndFileName = "";
        if (rentalDto.getPicture() != null)
            pathAndFileName = documentStorageService.storeFile(rentalDto.getPicture());
        String email = tokenService.getEmailFromToken(token);
        Rental rental = rentalService.createRental(userService.findUserByEmail(email).getId(), pathAndFileName, convertToEntity(rentalDto)); //Create rental in database
        if(rental != null) return ResponseEntity.ok(new GenericMessageDto("Rental created !"));
        else throw new InvalidUserException("error"); 
    }
    
    
    
    /**
     * Met à jour une location existante en fonction des données de demande et de l'ID de location fournis.
     * @param id
     * @param token Le jeton d’authentification obtenu à partir de l’en-tête de la demande.
     * @param rentalDto
     * @return
     * @throws IOException
     */
    
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Rental.class),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Interdit"),
            @ApiResponse(code = 404, message = "pas trouvé"),
            @ApiResponse(code = 500, message = "Une erreur s'est produite")
    })
    
    @ApiOperation(value = "Update rental",  produces = "application/json",notes ="Update rental avec les parameters(name, surface, price, picture and description) and owner_id from token")
    @PutMapping("/api/rentals/{id}")
    public ResponseEntity<GenericMessageDto> updateRentalById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token, @Valid @ModelAttribute RentalDto rentalDto) throws IOException {
       System.out.println("diengsalla");
        String email = tokenService.getEmailFromToken(token);
        Rental rental = rentalService.updateRental(id, userService.findUserByEmail(email).getId(), convertToEntity(rentalDto)); //Update rental in database
        if(rental != null) return ResponseEntity.ok(new GenericMessageDto("Rental updated !"));
        else throw new InvalidUserException("error");
    }
    

    private Rental convertToEntity(RentalDto rentalDto) {
        return modelMapper.map(rentalDto, Rental.class);
    }
}
