package com.petdoc.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "raca")
public class Raca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String especie;
}