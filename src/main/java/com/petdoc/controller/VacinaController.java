package com.petdoc.controller;

import com.petdoc.dto.VacinaCadastroDTO;
import com.petdoc.service.VacinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vacinas") // URL base para tudo relacionado a vacinas
public class VacinaController {

    @Autowired
    private VacinaService vacinaService;

    // Este método MOSTRA a página do formulário
    // A URL será, por exemplo: http://localhost:8080/vacinas/formulario/1 (para o pet de id 1)
    @GetMapping("/formulario/{petId}")
    public String carregaPaginaFormulario(@PathVariable Long petId, Model model) {
        // Adiciona o petId ao modelo para que o formulário saiba a qual pet a vacina pertence
        model.addAttribute("petId", petId);
        // Adiciona um DTO vazio para o Thymeleaf poder se conectar aos campos do formulário
        model.addAttribute("vacinaCadastroDTO", new VacinaCadastroDTO(null, null, null, petId));
        return "vacina/formulario"; // Retorna o caminho para o arquivo HTML
    }

    // Este método PROCESSA os dados enviados pelo formulário
    @PostMapping
    public String cadastraNovaVacina(VacinaCadastroDTO dados) {
        // O Spring preenche o objeto 'dados' com as informações do formulário
        vacinaService.cadastrar(dados);

        // Após salvar, redireciona o navegador para a página de detalhes do pet
        return "redirect:/pets/" + dados.petId(); // Ajuste essa URL se for diferente
    }
}