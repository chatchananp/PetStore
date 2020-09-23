package com.petstore.dto;

import com.petstore.model.Pet;

public class PhotoDTO {
	private Long photoId;
	private byte[] petPhotoData;
	private Pet pet;
	
	public Long getPhotoId() {
		return photoId;
	}
	
	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}
	
	public byte[] getPetPhotoData() {
		return petPhotoData;
	}
	
	public void setPetPhotoData(byte[] petPhotoData) {
		this.petPhotoData = petPhotoData;
	}
	
	public Pet getPet() {
		return pet;
	}
	
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
}
