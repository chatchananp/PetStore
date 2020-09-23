package com.petstore.dto;

public class PhotoDTO {
	private Long photoId;
	private byte[] petPhotoData;
	private Long petId;
	
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
	
	public Long getPet() {
		return petId;
	}
	
	public void setPet(Long petId) {
		this.petId = petId;
	}
	
}
