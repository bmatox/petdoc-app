package com.petdoc.service;

import com.petdoc.repository.PetRepository;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    // Lógica será implementada aqui nas próximas sprints
}