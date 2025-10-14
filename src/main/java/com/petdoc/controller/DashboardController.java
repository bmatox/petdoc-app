package com.petdoc.controller;

import com.petdoc.dto.PetCadastroDTO;
import com.petdoc.model.Pet;
import com.petdoc.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final PetService petService;

    public DashboardController(PetService petService) {
        this.petService = petService;
    }

    /**
     * Exibe a página principal do dashboard.
     * Busca a lista de pets do usuário logado e a envia para a view.
     */
    // Dentro de DashboardController.java

    @GetMapping("/dashboard")
    public String exibirDashboard(Model model) {
        List<Pet> petsDoTutor = petService.listarPetsDoTutor();
        model.addAttribute("pets", petsDoTutor);

        model.addAttribute("petDTO", new PetCadastroDTO(null, null, null, null));

        return "dashboard";
    }
}