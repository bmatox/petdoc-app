package com.petdoc.service;

import com.petdoc.dto.VacinaCadastroDTO;
import com.petdoc.model.Pet;
import com.petdoc.model.Vacina;
import com.petdoc.repository.PetRepository;
import com.petdoc.repository.VacinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacinaService {

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private PetRepository petRepository; // Precisamos dele para buscar o pet

    public void cadastrar(VacinaCadastroDTO dados) {
        // Busca a entidade Pet no banco usando o ID que veio do DTO
        Pet pet = petRepository.findById(dados.petId())
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado!"));

        // Cria um novo objeto Vacina vazio
        Vacina novaVacina = new Vacina();

        // Preenche o objeto com os dados do DTO
        novaVacina.setNomeVacina(dados.nomeVacina());
        novaVacina.setDataAplicacao(dados.dataAplicacao());
        novaVacina.setDataReforco(dados.dataReforco());
        novaVacina.setPet(pet); // Associa o objeto Pet completo

        // Manda o repositório salvar a nova vacina no banco
        vacinaRepository.save(novaVacina);
    }
}