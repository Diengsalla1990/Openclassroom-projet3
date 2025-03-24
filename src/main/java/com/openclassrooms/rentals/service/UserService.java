package com.openclassrooms.rentals.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.repository.UserRepository;

import lombok.Data;


/**
 * Couche de service pour la gestion de la logique métier liée aux utilisateurs.
 * Cette classe fournit des méthodes de gestion des utilisateurs,
 * telles que la création, la récupération et la suppression.
 */
@Data
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Creation d'un Utilisateur
     * @param user
     * @return enregistre entité user
     */
   
    public User createUser(User user) {
        user.setCreated_at(new Date()); 
        return userRepository.save(user);
    }
    
    /**
     * Rechercher un utilisateur avec l'e-mail comme paramètre
     * @param email
     * @return recherche l'email
     */

  
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email); 
    }
    
    /**
     * Vérification si l'email existe
     * @param email
     * @return
     */

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email); 
    }

}