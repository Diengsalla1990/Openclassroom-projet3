package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.model.Rental;
import com.openclassrooms.rentals.repository.RentalRepository;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;


/**
 * Couche de service pour la gestion de la logique métier liée aux utilisateurs.
 * Cette classe fournit des méthodes de gestion des Rentals,
 * telles que la création, la récupération et la suppression.
 */

@Data
@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    
    /**
     * recupération de tout les rental
     * @return tout les rentals
     */
    public Iterable<Rental> getAll(){
        return rentalRepository.findAll();
    }
    
    /**
     * Récuperation d'un rental par son Id
     * @param rentalId
     * @return rental par Id
     */

    
    public Rental getRental(Long rentalId){
        return rentalRepository.findById(rentalId).orElse(null);
    }
    
    /**
     * Creation d'un rental
     * @param ownerId
     * @param pathAndFileName
     * @param rental
     * @return
     */

    
    public Rental createRental(Long ownerId, String pathAndFileName, Rental rental){
       
        rental.setCreated_at(new Date());
        rental.setOwner_id(ownerId);
        rental.setPicture(pathAndFileName);
        return rentalRepository.save(rental);
    }
    
    /**
     * Mise à jour d'un rental
     * @param rentalId
     * @param ownerId
     * @param freshRental
     * @return rental update
     */

    //Update rental
    public Rental updateRental(Long rentalId, Long ownerId, Rental freshRental) {
        Rental rentalToUpdate = rentalRepository.findById(rentalId).orElse(null);
    
        if(rentalToUpdate != null && ownerId.equals(rentalToUpdate.getOwner_id())){
            
            if(freshRental.getName()!=null) rentalToUpdate.setName(freshRental.getName());
            if(freshRental.getSurface()!=null) rentalToUpdate.setSurface(freshRental.getSurface());
            if(freshRental.getPrice() != null) rentalToUpdate.setPrice(freshRental.getPrice());
            if(freshRental.getPicture() != null) rentalToUpdate.setPicture(freshRental.getPicture());
            if(freshRental.getDescription() != null)rentalToUpdate.setDescription(freshRental.getDescription());
            
            rentalToUpdate.setUpdated_at(new Date());
            return rentalRepository.save(rentalToUpdate);
        } else {
            return null;
        }
    }


}
