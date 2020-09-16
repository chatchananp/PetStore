package com.petstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petstore.model.Pet;
import com.petstore.model.Pet.Status;

public interface PetRepository extends JpaRepository<Pet, Long> {
	List<Pet> findByPetStatus(Status status);
}
