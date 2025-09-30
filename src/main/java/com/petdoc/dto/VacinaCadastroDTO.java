package com.petdoc.dto;

import java.time.LocalDate;

public record VacinaCadastroDTO(
        String nomeVacina,
        LocalDate dataAplicacao,
        LocalDate dataReforco,
        Long petId
) {
}