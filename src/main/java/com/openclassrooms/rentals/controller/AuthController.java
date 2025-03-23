package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.LoginDto;
import com.openclassrooms.rentals.dto.RegisterDto;
import com.openclassrooms.rentals.dto.TokenDto;
import com.openclassrooms.rentals.dto.UserDto;
import com.openclassrooms.rentals.exceptions.InvalidUserException;

import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired 
    TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    
    
    /**
     * 
     * @param loginDto
     * @return Obtenir un utilisateur et générer un jeton.
     */
    
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user ok", response = User.class),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Interdit"),
            @ApiResponse(code = 404, message = "pas trouvé"),
            @ApiResponse(code = 500, message = "Une erreur s'est produite")
    })
    @ApiOperation(value = "Autoriser un utilisateur à se connecter et à renvoyer un jeton",produces = "application/json")
    @PostMapping("/api/auth/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto) {
        User user = new User();
        user.setEmail(loginDto.getEmail());
        user.setPassword(loginDto.getPassword());
        User userFound = userService.findUserByEmail(user.getEmail());
        if (userFound != null) {
            
            if (!passwordEncoder.matches(user.getPassword(), userFound.getPassword()))
                throw new InvalidUserException("password not valid"); 
            else return ResponseEntity.ok(new TokenDto(tokenService.generateToken(userFound.getEmail()))); //Get user and generate a token
        } else {
        	throw new InvalidUserException("error"); 
        }
    }

    
    
    /**
     * Enregistre un nouveau compte utilisateur.
     * Vérifiez si l'utilisateur existe, sinon créez un utilisateur
     * @param registerDto
     * @return ResponseEntity retourne le token
     */
    
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rental created", response = User.class),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Interdit"),
            @ApiResponse(code = 404, message = "pas trouvé"),
            @ApiResponse(code = 500, message = "Une erreur s'est produite")
    })
    @ApiOperation(value = "Enregistrer un nouveau compte utilisateur",produces = "application/json")
    @PostMapping("/api/auth/register") 
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto) {
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        if (userService.existsByEmail(user.getEmail())) throw new InvalidUserException("User exist"); 
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        User userCreated = userService.createUser(user); 
        if (userCreated != null) {
            return ResponseEntity.ok(new TokenDto(tokenService.generateToken(user.getEmail()))); //If user created, then generate token
        } else {
             throw new InvalidUserException("error"); 
        }
    }
    
    /**
     * Obtenir L'information de l'utilisateur Connecter
     * @param token
     * @return renvoie le nom et l'email de l'utilisateur connecté
     */
    
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 401, message = "Non autorisé"),
            @ApiResponse(code = 403, message = "Interdit"),
            @ApiResponse(code = 404, message = "pas trouvé"),
            @ApiResponse(code = 500, message = "Une erreur s'est produite")
    })
    
    
    @Operation(summary = "Obtenir des informations sur l'utilisateur", description = "return name, email of user connected")
    @GetMapping("/api/auth/me") 
    public UserDto getUser( @RequestHeader("Authorization") String token) {
    	 
        String email = tokenService.getEmailFromToken(token); 
        
        System.out.println("API Key received: "+email);
        UserDto userDto = convertToDto(userService.findUserByEmail(email));
        return userDto; 
    }
    
    /**
     * mapper l'user objet à un UserDTO objet en utilisant ModelMapper
     * ModelMapper : utilise des conventions pour mapper automatiquement 
     * les propriétés portant le même nom dans les deux classes
     * @param user
     * @return
     */

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setPassword("");
        return userDto;
    }

}
