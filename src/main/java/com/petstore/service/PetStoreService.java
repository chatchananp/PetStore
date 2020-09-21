package com.petstore.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.petstore.dto.PetDTO;
import com.petstore.dto.PhotoDTO;
import com.petstore.exception.MethodArgumentNotValidEx;
import com.petstore.exception.ResourceNotFoundException;
import com.petstore.model.Pet;
import com.petstore.model.PetPhoto;
import com.petstore.repository.PetPhotoRepository;
import com.petstore.repository.PetRepository;

@Service
public class PetStoreService {

	@Autowired
	private PetRepository petRepo;
	@Autowired
	private PetPhotoRepository petPhotoRepo;
	@Autowired
	private ModelMapper modelMapper;

	private static final String PET_NOT_FOUND = "Pet not found for this id : ";

	public void addPet(PetDTO petDTO) {
		Pet pet = new ModelMapper().map(petDTO, Pet.class);
		petRepo.save(pet);
	}

	public List<PetDTO> getAllPet() throws ResourceNotFoundException {
		List<PetDTO> pets = petRepo.findAll().stream().map(this::convertToPetDTO).collect(Collectors.toList());

		if (pets == null || pets.isEmpty()) {
			throw new ResourceNotFoundException("Pet not found in database!");
		}

		return pets;
	}

	public PetDTO getPetById(String petId) throws ResourceNotFoundException, MethodArgumentNotValidEx {
		if (petId.matches("\\d+")) {
			Long longPetId = Long.parseLong(petId);
			return convertToPetDTO(petRepo.findById(longPetId)
					.orElseThrow(() -> new ResourceNotFoundException(PET_NOT_FOUND + petId)));
		} else {
			throw new MethodArgumentNotValidEx("Invalid pet id");
		}

	}

	public List<PetDTO> getPetByStatus(String status) throws ResourceNotFoundException {
		List<PetDTO> pets = petRepo.findByPetStatus(status).stream().map(this::convertToPetDTO)
				.collect(Collectors.toList());

		if (pets == null || pets.isEmpty()) {
			throw new ResourceNotFoundException("Pet not found for this status: " + status);
		}

		return pets;
	}

	public PetDTO updatePet(String petId, PetDTO petDTO) throws ResourceNotFoundException {
		Long longPetId = Long.parseLong(petId);
		PetDTO pickedPet = convertToPetDTO(
				petRepo.findById(longPetId).orElseThrow(() -> new ResourceNotFoundException(PET_NOT_FOUND + petId)));

		pickedPet.setPetId(petDTO.getPetId());
		pickedPet.setPetName(petDTO.getPetName());
		pickedPet.setPetStatus(petDTO.getPetStatus());
		Pet petUpdate = new ModelMapper().map(pickedPet, Pet.class);
		petRepo.save(petUpdate);

		return pickedPet;

	}

	public void deletePet(String petId) throws ResourceNotFoundException, MethodArgumentNotValidEx {
		if (petId.matches("\\d+")) {
			Long longPetId = Long.parseLong(petId);
			PetDTO pickedPet = convertToPetDTO(
					petRepo.findById(longPetId).orElseThrow(() -> new ResourceNotFoundException(PET_NOT_FOUND + petId)));
			Pet deletingPet = new ModelMapper().map(pickedPet, Pet.class);
			petRepo.delete(deletingPet);
		} else {
			throw new MethodArgumentNotValidEx("Invalid pet id");
		}
		
	}

	public void uploadPhoto(MultipartFile file, String petId) throws IOException, ResourceNotFoundException, MethodArgumentNotValidEx {
		if (petId.matches("\\d+")) {
			Long longPetId = Long.parseLong(petId);
			PetDTO pickedPet = convertToPetDTO(
					petRepo.findById(longPetId).orElseThrow(() -> new ResourceNotFoundException(PET_NOT_FOUND + petId)));

			PetPhoto photoFile = new PetPhoto(file.getBytes(), pickedPet.getPetId());
			petPhotoRepo.save(photoFile);
		} else {
			throw new MethodArgumentNotValidEx("Invalid pet id");
		}
		
	}

	public PhotoDTO getPetPhotoById(Long petId, Long photoId) throws ResourceNotFoundException {
		PetDTO pickedPet = convertToPetDTO(
				petRepo.findById(petId).orElseThrow(() -> new ResourceNotFoundException(PET_NOT_FOUND + petId)));

		return convertToPhotoDTO(petPhotoRepo.findByPetIdAndPhotoId(pickedPet.getPetId(), photoId)
				.orElseThrow(() -> new ResourceNotFoundException("Photo not found")));
	}

	private PetDTO convertToPetDTO(Pet pet) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper.map(pet, PetDTO.class);
	}

	private PhotoDTO convertToPhotoDTO(PetPhoto petPhoto) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper.map(petPhoto, PhotoDTO.class);
	}
}
