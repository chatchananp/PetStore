package com.petstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petstore.model.PetPhoto;

public interface PetPhotoRepository extends JpaRepository<PetPhoto, Long>{
	Optional<PetPhoto> findByPetIdAndPhotoId(Long petId, Long photoId);
}
