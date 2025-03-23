package com.openclassrooms.rentals.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.rentals.model.User;

/**
 * Classe en tant que Bean
 * Son rôle est de communiquer avec la BDD
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByEmail(String email);
    public boolean existsByEmail(String email);
    
}