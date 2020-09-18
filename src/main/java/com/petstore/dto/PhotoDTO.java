package com.petstore.dto;

public class PhotoDTO {
	private String photoId;
	private byte[] petPhotoData;
	private String petId;
	
	public String getPhotoId() {
		return photoId;
	}
	
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	
	public byte[] getPetPhotoData() {
		return petPhotoData;
	}
	
	public void setPetPhotoData(byte[] petPhotoData) {
		this.petPhotoData = petPhotoData;
	}
	
	public String getPetId() {
		return petId;
	}
	
	public void setPetId(String petId) {
		this.petId = petId;
	}
	
}
