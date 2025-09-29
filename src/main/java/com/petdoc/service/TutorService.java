package com.petdoc.service;

import com.petdoc.repository.TutorRepository;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    // Lógica de negócios será implementada aqui, mas só nas próximas sprints
}