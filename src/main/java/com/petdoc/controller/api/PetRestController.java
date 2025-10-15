package com.petdoc.controller.api;

import com.petdoc.dto.PetCadastroDTO;
import com.petdoc.model.Pet;
import com.petdoc.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API Controller para gerenciar Pets
 * Endpoints para operações CRUD que retornam JSON
 */
@RestController
@RequestMapping("/api/pets")
public class PetRestController {

    private final PetService petService;

    public PetRestController(PetService petService) {
        this.petService = petService;
    }

    /**
     * GET /api/pets
     * Lista todos os pets do tutor logado
     */
    @GetMapping
    public ResponseEntity<List<Pet>> listarPets() {
        List<Pet> pets = petService.listarPetsDoTutor();
        return ResponseEntity.ok(pets);
    }

    /**
     * GET /api/pets/{id}
     * Busca um pet específico por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPetPorId(@PathVariable Long id) {
        try {
            Pet pet = petService.buscarPetPorId(id);
            return ResponseEntity.ok(pet);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/pets
     * Cria um novo pet
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> criarPet(@Valid @RequestBody PetCadastroDTO petDTO) {
        try {
            petService.salvarPet(petDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Pet cadastrado com sucesso!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao cadastrar pet: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * PUT /api/pets/{id}
     * Atualiza um pet existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizarPet(
            @PathVariable Long id,
            @Valid @RequestBody PetCadastroDTO petDTO) {
        try {
            petService.atualizarPet(id, petDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Pet atualizado com sucesso!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao atualizar pet: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * DELETE /api/pets/{id}
     * Exclui um pet
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> excluirPet(@PathVariable Long id) {
        try {
            petService.excluirPet(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Pet excluído com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao excluir pet: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
