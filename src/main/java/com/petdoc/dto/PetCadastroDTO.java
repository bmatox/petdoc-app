package com.petdoc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record PetCadastroDTO(
        Long id, // Deixamos o ID nulo no cadastro, mas é útil para edição no futuro
        @NotBlank(message = "O nome do pet é obrigatório")
        String nome,

        @NotBlank(message = "A espécie é obrigatória")
        String especie,

        String raca,

        @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
        LocalDate dataNascimento
) {
    public PetCadastroDTO(String nome, String especie, String raca, LocalDate dataNascimento) {
        this(null, nome, especie, raca, dataNascimento);
    }
}