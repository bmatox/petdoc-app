package com.petdoc.backend.repository;

import com.petdoc.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // O Spring Data JPA cria a consulta automaticamente a partir do nome do método
    Optional<User> findByEmail(String email);

}