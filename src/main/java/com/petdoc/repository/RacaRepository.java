package com.petdoc.repository;

import com.petdoc.model.Raca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RacaRepository extends JpaRepository<Raca, Long> {
    List<Raca> findByEspecieOrderByNomeAsc(String especie);
}