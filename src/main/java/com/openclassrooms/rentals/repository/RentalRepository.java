package com.openclassrooms.rentals.repository;

import com.openclassrooms.rentals.model.Rental;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de référentiel pour la gestion des entités {@link Rental} dans la base de données.
 * Étend JpaRepository pour fournir des opérations CRUD et des requêtes personnalisées.
 */
@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {

}