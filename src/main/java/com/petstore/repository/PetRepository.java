package com.petstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petstore.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
	List<Pet> findByPetStatus(String status);
}
