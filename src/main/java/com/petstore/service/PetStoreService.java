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

	public PetDTO getPetById(String petId) throws ResourceNotFoundException {
		Long longPetId = Long.parseLong(petId);
		return convertToPetDTO(petRepo.findById(longPetId)
				.orElseThrow(() -> new ResourceNotFoundException("Pet not found for this id : " + petId)));

	}

	public List<PetDTO> getPetByStatus(String status) throws ResourceNotFoundException, MethodArgumentNotValidEx {
		if (status.matches("available|pending|sold")) {
			List<PetDTO> pets = petRepo.findByPetStatus(status).stream().map(this::convertToPetDTO)
					.collect(Collectors.toList());

			if (pets == null || pets.isEmpty()) {
				throw new ResourceNotFoundException("Pet not found for this status: " + status);
			}

			return pets;

		} else {
			throw new MethodArgumentNotValidEx("Invalid status value");
		}

	}

	public void updatePetByPost(String petId, PetDTO petDTO) throws ResourceNotFoundException {
		Long longPetId = Long.parseLong(petId);
		Pet pickedPet = petRepo.findById(longPetId)
				.orElseThrow(() -> new ResourceNotFoundException("Pet not found for this id : " + petId));

		pickedPet.setPetId(petDTO.getPetId());
		pickedPet.setPetName(petDTO.getPetName());
		pickedPet.setPetStatus(petDTO.getPetStatus());

		petRepo.save(pickedPet);
	}
	
	public void updatePetByPut(PetDTO petDTO) throws ResourceNotFoundException {
		Pet pickedPet = petRepo.findById(petDTO.getPetId())
				.orElseThrow(() -> new ResourceNotFoundException("Pet not found for this id : " + petDTO.getPetId()));
		
		pickedPet.setPetId(petDTO.getPetId());
		pickedPet.setPetName(petDTO.getPetName());
		pickedPet.setPetStatus(petDTO.getPetStatus());

		petRepo.save(pickedPet);
	}

	public void deletePet(String petId) {
		Long longPetId = Long.parseLong(petId);
		petRepo.deleteById(longPetId);

	}

	public void uploadPhoto(MultipartFile file, String petId) throws IOException {
		Long longPetId = Long.parseLong(petId);

		PetPhoto photoFile = new PetPhoto(file.getBytes(), longPetId);
		petPhotoRepo.save(photoFile);

	}

	public PhotoDTO getPetPhotoById(String petId, String photoId)
			throws ResourceNotFoundException, MethodArgumentNotValidEx {
		PetDTO pickedPet = getPetById(petId);

		if (photoId.matches("\\d+")) {
			Long longPhotoId = Long.parseLong(photoId);
			return convertToPhotoDTO(petPhotoRepo.findByPetIdAndPhotoId(pickedPet.getPetId(), longPhotoId)
					.orElseThrow(() -> new ResourceNotFoundException("Photo not found")));

		} else {
			throw new MethodArgumentNotValidEx("Invalid pet photo id");

		}

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
