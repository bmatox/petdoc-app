package com.petdoc.backend.controller;

import com.petdoc.backend.dto.UserRegisterDTO;
import com.petdoc.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO dto, BindingResult bindingResult) {
        // Se houver erros de validação (ex: e-mail inválido, senha curta), retorna erro 400
        if (bindingResult.hasErrors()) {
            // O ideal é retornar uma resposta estruturada com os erros, mas por enquanto isso funciona
            return ResponseEntity.badRequest().body("Erro de validação: " + bindingResult.getAllErrors().toString());
        }

        // Se os dados são válidos, chama o serviço para registrar o usuário
        userService.register(dto);

        // Retorna uma resposta de sucesso
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }
}