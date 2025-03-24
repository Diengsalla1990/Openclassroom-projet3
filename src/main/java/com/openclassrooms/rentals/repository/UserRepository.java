package com.openclassrooms.rentals.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.rentals.model.User;

/**
 * Interface de référentiel pour la gestion des entités {@link User} dans la base de données.
 * Étend JpaRepository pour fournir des opérations CRUD et des requêtes personnalisées.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByEmail(String email);
    public boolean existsByEmail(String email);
    
}