package com.petdoc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record PetCadastroDTO(
        @NotBlank(message = "O nome do pet é obrigatório")
        String nome,

        String especie,
        String raca,

        @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
        LocalDate dataNascimento
) {
}