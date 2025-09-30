package com.petdoc.controller;

import com.petdoc.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    // Métodos para listar, cadastrar, editar pets serão criados aqui
}