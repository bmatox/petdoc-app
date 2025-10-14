package com.petdoc.controller;

import com.petdoc.dto.PetCadastroDTO;
import com.petdoc.model.Pet;
import com.petdoc.service.PetService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pets") // Define que todas as URLs deste controller começarão com /pets
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    /**
     * GET /pets/novo
     * Exibe o formulário de cadastro de um novo pet.
     */
    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        // Envia um objeto DTO vazio para o formulário ser preenchido
        model.addAttribute("petDTO", new PetCadastroDTO(null, null, null, null));
        // Sinaliza que o formulário está em modo de "criação"
        model.addAttribute("isEditMode", false);
        return "pets/pet-form";
    }

    /**
     * POST /pets
     * Processa o salvamento de um novo pet.
     */
    @PostMapping
    public String salvarNovoPet(@Valid @ModelAttribute("petDTO") PetCadastroDTO petDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Se houver erros de validação, retorna para o formulário
            return "pets/pet-form";
        }
        petService.salvarPet(petDTO);
        redirectAttributes.addFlashAttribute("sucesso", "Pet cadastrado com sucesso!");
        return "redirect:/dashboard";
    }

    /**
     * GET /pets/{id}/editar
     * Exibe o formulário de edição de um pet existente.
     */
    @GetMapping("/{id}/editar")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        Pet pet = petService.buscarPetPorId(id);
        // Converte a entidade de volta para um DTO para preencher o formulário
        PetCadastroDTO petDTO = new PetCadastroDTO(pet.getNome(), pet.getEspecie(), pet.getRaca(), pet.getDataNascimento());

        model.addAttribute("petDTO", petDTO);
        model.addAttribute("petId", id); // Envia o ID para a action do formulário de edição
        model.addAttribute("isEditMode", true); // Sinaliza que o formulário está em modo de "edição"

        return "pets/pet-form";
    }

    /**
     * POST /pets/{id}
     * Processa a atualização de um pet existente.
     */
    @PostMapping("/{id}")
    public String atualizarPet(@PathVariable Long id,
                               @Valid @ModelAttribute("petDTO") PetCadastroDTO petDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Se houver erros, retorna para o formulário de edição
            redirectAttributes.addFlashAttribute("petId", id); // Reenvia o ID em caso de erro
            return "pets/pet-form";
        }
        petService.atualizarPet(id, petDTO);
        redirectAttributes.addFlashAttribute("sucesso", "Dados do pet atualizados com sucesso!");
        return "redirect:/dashboard";
    }

    /**
     * POST /pets/{id}/excluir
     * Processa a exclusão de um pet.
     */
    @PostMapping("/{id}/excluir")
    public String excluirPet(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            petService.excluirPet(id);
            redirectAttributes.addFlashAttribute("sucesso", "Pet excluído com sucesso.");
        } catch (Exception e) {
            // Captura qualquer erro (ex: pet não encontrado, falha de permissão)
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir o pet: " + e.getMessage());
        }
        return "redirect:/dashboard";
    }
}