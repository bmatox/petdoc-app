package com.petdoc.controller.api;

import com.petdoc.dto.RacaDTO;
import com.petdoc.service.RacaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/racas")
public class RacaApiController {

        private final RacaService racaService;

    public RacaApiController(RacaService racaService) {
        this.racaService = racaService;
    }

    @GetMapping
    public List<RacaDTO> getRacasPorEspecie(@RequestParam String especie) {
        return racaService.buscarRacasPorEspecie(especie);
    }
}