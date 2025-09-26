package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String especie;

    private String raca;

    @Column(name = "data_nascimento") // JPA: Mapeia o campo Java "dataNascimento" para a coluna "data_nascimento" no banco
    private LocalDate dataNascimento;

    // Relacionamento: Muitos Pets pertencem a um Tutor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id", nullable = false) // JPA: Define a coluna da chave estrangeira ("tutor_id")
    private Tutor tutor;

    // Relacionamento: Um Pet pode ter muitas Vacinas
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacina> vacinas;
}