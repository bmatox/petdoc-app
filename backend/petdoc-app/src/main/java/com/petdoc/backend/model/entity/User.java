package com.petdoc.backend.model.entity;

import jakarta.persistence.*;
import lombok.Data; // Importante ter o import do Lombok

@Entity
@Table(name = "tb_users")
@Data // Esta anotação cria getters, setters, toString, etc.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;
}