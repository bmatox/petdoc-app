package com.petdoc.api.controller;

import com.petdoc.api.dto.ErrorResponseDTO;
import com.petdoc.api.dto.LoginRequestDTO;
import com.petdoc.api.dto.LoginResponseDTO;
import com.petdoc.model.Tutor;
import com.petdoc.repository.TutorRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para autenticação via API
 * Suporta o frontend Vue.js moderno
 */
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final TutorRepository tutorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthApiController(TutorRepository tutorRepository, PasswordEncoder passwordEncoder) {
        this.tutorRepository = tutorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Endpoint para login via API REST
     * Realiza autenticação e cria sessão Spring Security
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequestDTO loginRequest,
            BindingResult bindingResult,
            HttpServletRequest request) {

        // Valida erros de binding
        if (bindingResult.hasErrors()) {
            List<String> erros = bindingResult.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    HttpStatus.BAD_REQUEST.value(),
                    "Erro de validação",
                    "Os dados fornecidos são inválidos",
                    request.getRequestURI(),
                    erros
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            // Busca o tutor pelo email
            Tutor tutor = tutorRepository.findByEmail(loginRequest.email())
                    .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

            // Verifica a senha
            if (!passwordEncoder.matches(loginRequest.senha(), tutor.getPassword())) {
                throw new RuntimeException("Credenciais inválidas");
            }

            // Cria a autenticação do Spring Security
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    tutor,
                    tutor.getPassword(),
                    tutor.getAuthorities()
            );

            // Define o contexto de segurança
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            // Salva o contexto na sessão
            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    securityContext
            );

            // Cria a resposta de sucesso
            LoginResponseDTO.DadosUsuarioDTO dadosUsuario = 
                    new LoginResponseDTO.DadosUsuarioDTO(tutor.getNome(), tutor.getEmail());
            
            LoginResponseDTO response = new LoginResponseDTO(
                    true,
                    "Login realizado com sucesso",
                    "/dashboard",
                    dadosUsuario
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Falha na autenticação",
                    "Email ou senha inválidos",
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    /**
     * Endpoint para verificar se o usuário está autenticado
     */
    @GetMapping("/status")
    public ResponseEntity<?> checkAuthStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() 
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            
            Tutor tutor = (Tutor) authentication.getPrincipal();
            LoginResponseDTO.DadosUsuarioDTO dadosUsuario = 
                    new LoginResponseDTO.DadosUsuarioDTO(tutor.getNome(), tutor.getEmail());
            
            LoginResponseDTO response = new LoginResponseDTO(
                    true,
                    "Usuário autenticado",
                    "/dashboard",
                    dadosUsuario
            );
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Não autenticado",
                        "Usuário não está autenticado",
                        "/api/auth/status"
                ));
    }
}
