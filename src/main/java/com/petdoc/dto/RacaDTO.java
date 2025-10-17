package com.petdoc.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RacaDTO(
        @JsonProperty("name")
        String nome
) {}