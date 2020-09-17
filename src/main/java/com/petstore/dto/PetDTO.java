package com.petstore.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.petstore.model.PetPhoto;

import com.petstore.exception.Enum;

public class PetDTO {
	private Long petId;
	
	@NotBlank(message = "Please insert pet name")
	private String petName;
	
	private List<PetPhoto> petPhotos;
	
	public enum Status {
		available, pending, sold
	}
	
	//@NotBlank(message = "Please insert pet status")
	@Enum(enumClass=Status.class, ignoreCase=true)
	private String petStatus;

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

	public List<PetPhoto> getPetPhotos() {
		return petPhotos;
	}

	public void setPetPhotos(List<PetPhoto> petPhotos) {
		this.petPhotos = petPhotos;
	}

	public String getPetStatus() {
		return petStatus;
	}

	public void setPetStatus(String petStatus) {
		this.petStatus = petStatus;
	}
	
}
