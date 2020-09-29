package com.petstore.dto;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.petstore.validator.CustomEnumAnnotation;

public class PetDTO {
	private Long petId;
	
	@NotBlank(message = "Please insert pet name")
	private String petName;
	
	private List<PhotoDTO> petPhotos;
	
	public enum Status {
		available, pending, sold
	}
	
	@CustomEnumAnnotation(enumClass=Status.class, ignoreCase=true)
	private String petStatus;
	
	private UUID petRandomId;

	public Long getPetId() {
		return petId;
	}

	public void setPetId(Long petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public List<PhotoDTO> getPetPhotos() {
		return petPhotos;
	}

	public void setPetPhotos(List<PhotoDTO> petPhotos) {
		this.petPhotos = petPhotos;
	}

	public String getPetStatus() {
		return petStatus;
	}

	public void setPetStatus(String petStatus) {
		this.petStatus = petStatus;
	}

	public UUID getPetRandomId() {
		return petRandomId;
	}

	public void setPetRandomId(UUID petRandomId) {
		this.petRandomId = petRandomId;
	}

	
	
}
