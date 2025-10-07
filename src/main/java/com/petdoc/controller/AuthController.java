package com.petdoc.controller;

import com.petdoc.dto.TutorCadastroDTO;
import com.petdoc.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final TutorService tutorService;

    public AuthController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @GetMapping("/login")
    public String exibirPaginaLogin() {
        return "auth/login";
    }

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("tutor", new TutorCadastroDTO("", "", ""));
        return "auth/cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@Valid @ModelAttribute("tutor") TutorCadastroDTO tutorDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/cadastro";
        }

        try {
            tutorService.cadastrar(tutorDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Cadastro realizado com sucesso! Por favor, faça o login.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("erroGeral", e.getMessage());
            return "auth/cadastro";
        }
    }

    @GetMapping("/dashboard")
    public String exibirDashboard() {
        // No futuro, aqui buscaremos dados (pets, vacinas) para mostrar no dashboard
        return "dashboard"; // Retorna o nome do arquivo dashboard.html
    }
}