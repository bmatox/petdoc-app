package com.petdoc.service;

import com.petdoc.dto.PetCadastroDTO;
import com.petdoc.model.Pet;
import com.petdoc.model.Tutor;
import com.petdoc.repository.PetRepository;
import com.petdoc.security.UsuarioLogadoService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UsuarioLogadoService usuarioLogadoService;

    public PetService(PetRepository petRepository, UsuarioLogadoService usuarioLogadoService) {
        this.petRepository = petRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    @Transactional
    public void salvarPet(PetCadastroDTO dto) {
        Tutor tutorLogado = usuarioLogadoService.getTutorAutenticado();
        Pet novoPet = new Pet();
        novoPet.setNome(dto.nome());
        novoPet.setEspecie(dto.especie());
        novoPet.setRaca(dto.raca());
        novoPet.setDataNascimento(dto.dataNascimento());
        novoPet.setTutor(tutorLogado);
        petRepository.save(novoPet);
    }

    @Transactional
    public void atualizarPet(Long petId, PetCadastroDTO dto) {
        Pet petParaAtualizar = buscarPetDoTutorLogado(petId); // Garante a autorização
        petParaAtualizar.setNome(dto.nome());
        petParaAtualizar.setEspecie(dto.especie());
        petParaAtualizar.setRaca(dto.raca());
        petParaAtualizar.setDataNascimento(dto.dataNascimento());
        petRepository.save(petParaAtualizar);
    }

    @Transactional
    public void excluirPet(Long petId) {
        buscarPetDoTutorLogado(petId); // Garante a autorização antes de excluir
        petRepository.deleteById(petId);
    }

    public List<Pet> listarPetsDoTutor() {
        try {
            Tutor tutorLogado = usuarioLogadoService.getTutorAutenticado();
            return petRepository.findByTutorId(tutorLogado.getId());
        } catch (IllegalStateException e) {
            return Collections.emptyList();
        }
    }

    public Pet buscarPetPorId(Long petId) {
        return buscarPetDoTutorLogado(petId);
    }

    /**
     * Metodo auxiliar que busca um pet e valida se ele pertence ao tutor logado.
     */
    private Pet buscarPetDoTutorLogado(Long petId) {
        Tutor tutorLogado = usuarioLogadoService.getTutorAutenticado();

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet com ID " + petId + " não encontrado."));

        // Regra de Autorização
        if (!pet.getTutor().getId().equals(tutorLogado.getId())) {
            throw new AccessDeniedException("Você não tem permissão para acessar este recurso.");
        }
        return pet;
    }
}