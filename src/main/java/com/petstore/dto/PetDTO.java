package com.petstore.dto;

import java.util.List;

import com.petstore.model.PetPhoto;

public class PetDTO {
	private Long petId;
	private String petName;
	private List<PetPhoto> petPhotos;
	
	public enum Status {
		available, pending, sold
	}
	
	private Status petStatus;

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

	public Status getPetStatus() {
		return petStatus;
	}

	public void setPetStatus(Status petStatus) {
		this.petStatus = petStatus;
	}
	
}
