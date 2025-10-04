package com.petdoc.service;

import com.petdoc.dto.TutorCadastroDTO;
import com.petdoc.model.Tutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest // Annotation para carregar o contexto completo do Spring Boot para o teste
@Transactional  // Faz com que cada teste rode em uma transação que é desfeita no final (não suja o banco)
class TutorServiceTest {

    @Autowired
    private TutorService tutorService;

    @Test
    void deveCadastrarNovoTutorComSucesso() {
        // ARRANGE (Preparar)
        var dto = new TutorCadastroDTO("José Almeida", "jose.almeida@teste.com", "senha123");

        // ACT (Agir)
        Tutor tutorSalvo = tutorService.cadastrar(dto);

        // ASSERT (Verificar)
        Assertions.assertNotNull(tutorSalvo.getId());
        Assertions.assertEquals("José Almeida", tutorSalvo.getNome());
        // Verifica se a senha foi criptografada (não é mais igual à original)
        Assertions.assertNotEquals("senha123", tutorSalvo.getSenha());
    }

    @Test
    void naoDeveCadastrarTutorComEmailDuplicado() {
        // ARRANGE (Preparar)
        // Primeiro, cadastramos um tutor
        var dto1 = new TutorCadastroDTO("Maria Silva", "maria.silva@teste.com", "senha123");
        tutorService.cadastrar(dto1);

        // Preparamos um segundo DTO com o MESMO e-mail
        var dto2 = new TutorCadastroDTO("Maria", "maria.silva@teste.com", "outrasenha");

        // ACT & ASSERT (Agir e Verificar)
        // Verificamos se, ao tentar cadastrar o segundo DTO, uma exceção do tipo RuntimeException é lançada.
        Assertions.assertThrows(RuntimeException.class, () -> tutorService.cadastrar(dto2));
    }
}