package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.GenericMessageDto;
import com.openclassrooms.rentals.dto.MessageDto;
import com.openclassrooms.rentals.exceptions.InvalidUserException;
import com.openclassrooms.rentals.model.Message;
import com.openclassrooms.rentals.service.MessageService;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
* Contrôleur REST pour la gestion des requêtes HTTP liées aux Message
* Cette classe expose lepoints de terminaison pour l'opérations Message
* la création de Message
*/

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ModelMapper modelMapper;
    
    /**
     * Envoyer un message avec les paramètres : rental_id et message
     * @param token
     * @param messageDto
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Message.class),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Interdit"),
            @ApiResponse(code = 404, message = "pas trouvé"),
            @ApiResponse(code = 500, message = "Une erreur s'est produite")
    })
    @ApiOperation(value = "Envoyer un message avec les paramètres : rental_id et message",
            produces = "application/json")
    @PostMapping("/api/messages")
    public ResponseEntity<GenericMessageDto> createMessage(@RequestHeader("Authorization") String token, @Valid @RequestBody MessageDto messageDto) {
    	String email = tokenService.getEmailFromToken(token);
    	Message message = messageService.createMessage(userService.findUserByEmail(email).getId(), convertToEntity(messageDto)); //Create message
    	if(message != null) return ResponseEntity.ok(new GenericMessageDto("Message sent with success"));
    	else throw new InvalidUserException("error"); 
    }
    

    private Message convertToEntity(MessageDto messageDto) {
        return modelMapper.map(messageDto, Message.class);
    }
}
