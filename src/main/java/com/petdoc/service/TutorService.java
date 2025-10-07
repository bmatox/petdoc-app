package com.petdoc.service;

import com.petdoc.dto.TutorCadastroDTO;
import com.petdoc.model.Tutor;
import com.petdoc.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Tutor cadastrar(TutorCadastroDTO dto) {
        // 1) Verificar se o e-mail já existe no banco
        if (tutorRepository.findByEmail(dto.email()).isPresent()) {
            // Se o Optional não estiver vazio, significa que já existe um usuário com esse e-mail.
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }

        // 2) Criptografar a senha recebida
        var senhaCriptografada = passwordEncoder.encode(dto.senha());

        // Cria a nova entidade Tutor
        Tutor novoTutor = new Tutor();
        novoTutor.setNome(dto.nome());
        novoTutor.setEmail(dto.email());
        novoTutor.setSenha(senhaCriptografada); // Salva a senha JÁ CRIPTOGRAFADA

        // 3) Salvar o novo Tutor no banco de dados
        return tutorRepository.save(novoTutor);
    }
}