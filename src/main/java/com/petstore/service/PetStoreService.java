package com.petstore.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.petstore.dto.PetDTO;
import com.petstore.dto.PhotoDTO;
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

	public List<PetDTO> getAllPet() {
		return petRepo.findAll().stream().map(this::convertToPetDTO).collect(Collectors.toList());
	}

	public PetDTO getPetById(Long petId) throws ResourceNotFoundException {
		return convertToPetDTO(petRepo.findById(petId)
				.orElseThrow(() -> new ResourceNotFoundException(PET_NOT_FOUND + petId)));
	}

	public Stream<PetDTO> getPetByStatus(String status) {
		return petRepo.findByPetStatus(status).stream().map(this::convertToPetDTO);
	}

	public PetDTO updatePet(Long petId, PetDTO petDTO) throws ResourceNotFoundException {
		PetDTO pickedPet = convertToPetDTO(petRepo.findById(petId)
				.orElseThrow(() -> new ResourceNotFoundException(PET_NOT_FOUND + petId)));
		
		pickedPet.setPetId(petDTO.getPetId());
		pickedPet.setPetName(petDTO.getPetName());
		pickedPet.setPetStatus(petDTO.getPetStatus());
		Pet petUpdate = new ModelMapper().map(pickedPet, Pet.class);
		petRepo.save(petUpdate);

		return pickedPet;
	}

	public void deletePet(Long petId) throws ResourceNotFoundException {
		PetDTO pickedPet = convertToPetDTO(petRepo.findById(petId)
				.orElseThrow(() -> new ResourceNotFoundException(PET_NOT_FOUND + petId)));
		Pet deletingPet = new ModelMapper().map(pickedPet, Pet.class);
		petRepo.delete(deletingPet);
	}

	public void uploadPhoto(MultipartFile file, Long petId) throws IOException, ResourceNotFoundException {
		PetDTO pickedPet = convertToPetDTO(petRepo.findById(petId)
				.orElseThrow(() -> new ResourceNotFoundException(PET_NOT_FOUND + petId)));
		
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		PetPhoto photoFile = new PetPhoto(fileName, file.getBytes(), pickedPet.getPetId());
		petPhotoRepo.save(photoFile);
	}

	public Optional<PhotoDTO> getPetPhotoById(Long petId, Long photoId) throws ResourceNotFoundException {
		PetDTO pickedPet = convertToPetDTO(petRepo.findById(petId)
				.orElseThrow(() -> new ResourceNotFoundException(PET_NOT_FOUND + petId)));
		
		PhotoDTO pickedPetPhoto = convertToPhotoDTO(petPhotoRepo.findById(photoId)
				.orElseThrow(() -> new ResourceNotFoundException("Photo not found")));
		
		return petPhotoRepo.findByPetIdAndPhotoId(pickedPet.getPetId(), pickedPetPhoto.getPhotoId()).map(this::convertToPhotoDTO);
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
