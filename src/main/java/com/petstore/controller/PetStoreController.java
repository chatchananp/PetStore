package com.petstore.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.petstore.dto.PetDTO;
import com.petstore.dto.PhotoDTO;
import com.petstore.exception.ResourceNotFoundException;
import com.petstore.service.PetStoreService;

@RestController
public class PetStoreController {

	@Autowired
	PetStoreService petStoreService;

	@PostMapping(value = "/pet")
	public ResponseEntity<String> addPet(@Valid @RequestBody PetDTO petDTO) {
		petStoreService.addPet(petDTO);
		return ResponseEntity.ok("Add pet successful");
	}

	@GetMapping(value = "/pet")
	public ResponseEntity<List<PetDTO>> getAllPet() throws ResourceNotFoundException {
		return ResponseEntity.ok().body(petStoreService.getAllPet());
	}

	@GetMapping(value = "/pet/{id}")
	public ResponseEntity<PetDTO> getPetById(@PathVariable(name = "id") Long petId) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(petStoreService.getPetById(petId));
	}

	@GetMapping(value = "/pet/status/{status}")
	public ResponseEntity<List<PetDTO>> getPetByStatus(@PathVariable(name = "status") String status) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(petStoreService.getPetByStatus(status));
	}

	@PutMapping("/pet/{id}")
	public ResponseEntity<String> updatePet(@PathVariable(name = "id") Long petId, @Valid @RequestBody PetDTO petDTO) throws ResourceNotFoundException {
		petDTO.setPetId(petId);
		petStoreService.updatePet(petId, petDTO);
		return ResponseEntity.ok("Update pet successful");
	}

	@PostMapping(value = "/pet/{id}/uploadImage")
	public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file, @PathVariable(name = "id") Long petId) throws IOException, ResourceNotFoundException {
		petStoreService.uploadPhoto(file, petId);
		return ResponseEntity.ok("Upload photo successful");
	}

	@GetMapping(value = "/pet/{id}/photo/{photoId}")
	public ResponseEntity<byte[]> getPetPhotoById(@PathVariable(name = "id") Long petId,
										     @PathVariable(name = "photoId") Long photoId) throws ResourceNotFoundException {
		
		PhotoDTO petPhoto = petStoreService.getPetPhotoById(petId, photoId);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(petPhoto.getPetPhotoData());
		
	}

	@DeleteMapping("/pet/{id}")
	public ResponseEntity<String> deletePet(@PathVariable(name = "id") Long petId) throws ResourceNotFoundException {
		this.petStoreService.deletePet(petId);
		return ResponseEntity.ok("Delete pet successful");
	}
}
