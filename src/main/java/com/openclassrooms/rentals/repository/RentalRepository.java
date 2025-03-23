package com.openclassrooms.rentals.repository;

import com.openclassrooms.rentals.model.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe en tant que Bean
 * Son rôle est de communiquer avec la BDD
 */
@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {

}