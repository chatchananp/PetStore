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
import com.petstore.model.Pet;
import com.petstore.model.Pet.Status;
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
	
	public List<PetDTO> getAllPet() {
		return petRepo
				.findAll().stream()
				.map(this::convertToPetDTO)
				.collect(Collectors.toList());
	}
	
	public PetDTO getPetById(Long petId) {
		return convertToPetDTO(petRepo.findById(petId).get());
	}
	
	public Stream<PetDTO> getPetByStatus(Status status) {
		return petRepo.findByPetStatus(status).stream().map(this::convertToPetDTO);
	}
	
	public PetDTO updatePet(PetDTO petDTO) {
        Optional < PetDTO > pickedPet = petRepo.findById(petDTO.getPetId()).map(this::convertToPetDTO);

        if (pickedPet.isPresent()) {
            PetDTO petDTOUpdate = pickedPet.get();
            petDTOUpdate.setPetId(petDTO.getPetId());
            petDTOUpdate.setPetName(petDTO.getPetName());
            petDTOUpdate.setPetStatus(petDTO.getPetStatus());
            Pet petUpdate = new ModelMapper().map(petDTOUpdate, Pet.class);
            petRepo.save(petUpdate);
            
            return petDTOUpdate;
        } else {
        	return null;
        }
    }
	
	public void deletePet(Long petId) {
        Optional < PetDTO > pickedPet = petRepo.findById(petId).map(this::convertToPetDTO);
        PetDTO petDTODeleting = pickedPet.get();
        Pet deletingPet = new ModelMapper().map(petDTODeleting, Pet.class);
        petRepo.delete(deletingPet);
    }
	
	public PetPhoto uploadPhoto(MultipartFile file, Long petId) throws IOException {
		// Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        PetPhoto photoFile = new PetPhoto(fileName, file.getBytes(), petId);
        return petPhotoRepo.save(photoFile);
	}
	
	public Optional<PhotoDTO> getPetPhotoById(Long petId, Long photoId) {
		return petPhotoRepo.findByPetIdAndPhotoId(petId, photoId).map(this::convertToPhotoDTO);
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
