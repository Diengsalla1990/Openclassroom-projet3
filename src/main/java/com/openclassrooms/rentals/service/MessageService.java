package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.model.Message;
import com.openclassrooms.rentals.repository.MessageRepository;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;


/**
 * Couche de service pour la gestion de la logique métier liée aux utilisateurs.
 * Cette classe fournit des méthodes de gestion des messages,
 * telles que la création, la récupération et la suppression.
 */
@Data
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    //Message creation
    
    /**
     * Création d'un message
     * @param userId
     * @param message
     * @return
     */
    public Message createMessage(Long userId, Message message) {
        message.setUser_id(userId);
        message.setCreatedAt(new Date());
        return messageRepository.save(message);
    }




}