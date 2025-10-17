package com.petdoc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vacina")
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_vacina", nullable = false)
    private String nomeVacina;

    @Column(name = "data_aplicacao", nullable = false)
    private LocalDate dataAplicacao;

    @Column(name = "data_reforco")
    private LocalDate dataReforco;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}