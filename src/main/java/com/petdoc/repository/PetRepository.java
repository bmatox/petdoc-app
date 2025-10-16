package com.petdoc.repository;

import com.petdoc.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByTutorId(Long tutorId);
    List<Pet> findByTutorIdAndEspecie(Long tutorId, String especie);
}