package com.petstore.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
import com.petstore.model.Pet.Status;
import com.petstore.service.PetStoreService;

@RestController
public class PetStoreController {

	@Autowired
	PetStoreService petStoreService;

	@PostMapping(value = "/pet")
	public ResponseEntity<String> addPet(@RequestBody PetDTO petDTO) {
		petStoreService.addPet(petDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/pet")
	public ResponseEntity<List<PetDTO>> getAllPet() {
		return ResponseEntity.ok().body(petStoreService.getAllPet());
	}

	@GetMapping(value = "/pet/{id}")
	public ResponseEntity<Optional<PetDTO>> getPetById(@PathVariable(name = "id") Long petId) {
		return ResponseEntity.ok().body(petStoreService.getPetById(petId));
	}

	@GetMapping(value = "/pet/status/{status}")
	public ResponseEntity<Stream<PetDTO>> getPetByStatus(@PathVariable(name = "status") Status status) {
		return ResponseEntity.ok().body(petStoreService.getPetByStatus(status));
	}

	@PutMapping("/pet/{id}")
	public ResponseEntity<String> updatePet(@PathVariable(name = "id") Long petId, @RequestBody PetDTO petDTO) {
		petDTO.setPetId(petId);
		petStoreService.updatePet(petDTO);
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/pet/{id}/uploadImage")
	public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file, @PathVariable(name = "id") Long petId) throws IOException {
		petStoreService.uploadPhoto(file, petId);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/pet/{id}/photo/{photoId}")
	public ResponseEntity<byte[]> getPetPhotoById(@PathVariable(name = "id") Long petId, 
										     @PathVariable(name = "photoId") Long photoId) {
		
		Optional<PhotoDTO> petPhotoOptional = petStoreService.getPetPhotoById(petId, photoId);
		PhotoDTO petPhotoFile = petPhotoOptional.get();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(petPhotoFile.getPetPhotoData());
		
	}

	@DeleteMapping("/pet/{id}")
	public ResponseEntity<String> deletePet(@PathVariable(name = "id") Long petId) {
		this.petStoreService.deletePet(petId);
		return ResponseEntity.ok().build();
	}
}
