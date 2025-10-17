package com.petdoc.service;

import com.petdoc.dto.RacaDTO;
import com.petdoc.repository.RacaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RacaService {

    private final RacaRepository racaRepository;

    public RacaService(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    public List<RacaDTO> buscarRacasPorEspecie(String especie) {
        return racaRepository.findByEspecieOrderByNomeAsc(especie)
                .stream()
                .map(raca -> new RacaDTO(raca.getNome())) // Converte a Entidade para DTO
                .collect(Collectors.toList());
    }
}