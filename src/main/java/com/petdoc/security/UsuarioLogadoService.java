package com.petdoc.security;

import com.petdoc.model.Tutor;
import com.petdoc.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogadoService {

    private final TutorRepository tutorRepository;

    @Autowired
    public UsuarioLogadoService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }
    /**
     * Retorna a entidade completa do Tutor que está autenticado no sistema.
     *
     * @return O objeto Tutor correspondente ao usuário logado.
     * @throws IllegalStateException se não houver usuário autenticado ou se o usuário não for encontrado no banco.
     */
    public Tutor getTutorAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Nenhum usuário autenticado encontrado na sessão.");
        }
        String email = authentication.getName();
        return tutorRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuário autenticado não encontrado no banco de dados."));
    }
}