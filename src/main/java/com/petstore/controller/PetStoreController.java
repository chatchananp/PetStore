package com.petstore.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.petstore.dto.PetDTO;
import com.petstore.dto.PhotoDTO;
import com.petstore.exception.MethodArgumentNotValidEx;
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
	public ResponseEntity<PetDTO> getPetById(@PathVariable(name = "id") String petId) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(petStoreService.getPetById(petId));
	}

	@GetMapping(value = "/pet/status/{status}")
	public ResponseEntity<List<PetDTO>> getPetByStatus(@PathVariable(name = "status") String status)
			throws ResourceNotFoundException, MethodArgumentNotValidEx {
		return ResponseEntity.ok().body(petStoreService.getPetByStatus(status));
	}

	@PutMapping("/pet")
	public ResponseEntity<String> updatePetByPut(@Valid @RequestBody PetDTO petDTO) throws ResourceNotFoundException {
		petStoreService.updatePetByPut(petDTO);
		return ResponseEntity.ok("Update pet successful");
	}

	@PostMapping("/pet/{id}")
	public ResponseEntity<String> updatePetByPost(@PathVariable(name = "id") String petId,
			@Valid @RequestBody PetDTO petDTO) throws ResourceNotFoundException {
		Long longPetId = Long.parseLong(petId);
		petDTO.setPetId(longPetId);
		petStoreService.updatePetByPost(petId, petDTO);
		return ResponseEntity.ok("Update pet successful");
	}

	@PostMapping(value = "/pet/{id}/uploadImage")
	public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file,
			@PathVariable(name = "id") String petId) throws IOException, ResourceNotFoundException {
		petStoreService.uploadPhoto(file, petId);
		return ResponseEntity.ok("Upload photo successful");
	}

	@GetMapping(value = "/pet/{id}/photo/{photoId}")
	public ResponseEntity<byte[]> getPetPhotoById(@PathVariable(name = "id") String petId,
			@PathVariable(name = "photoId") String photoId) throws ResourceNotFoundException {

		PhotoDTO petPhoto = petStoreService.getPetPhotoById(petId, photoId);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(petPhoto.getPetPhotoData());

	}

	@DeleteMapping("/pet/{id}")
	public ResponseEntity<String> deletePet(@PathVariable(name = "id") String petId) throws ResourceNotFoundException {
		petStoreService.deletePet(petId);
		return ResponseEntity.ok("Delete pet successful");
	}

	@GetMapping("/pet/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException, ResourceNotFoundException {
		response.setContentType("text/csv");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		String currentDateTime = dateFormatter.format(new Date());

		String filename = "pets_data_at_" + currentDateTime + ".csv";
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + filename;
		response.setHeader(headerKey, headerValue);

		List<PetDTO> listPets = petStoreService.getAllPet();

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = { "id", "name", "status" };
		String[] nameMapping = { "petId", "petName", "petStatus" };

		csvWriter.writeHeader(csvHeader);

		for (PetDTO pet : listPets) {
			csvWriter.write(pet, nameMapping);
		}

		csvWriter.close();
	}
}
