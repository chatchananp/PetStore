package com.petstore.dto;

public class PhotoDTO {
	private Long photoId;
	private String petPhotoName;
	private byte[] petPhotoData;
	private Long petId;
	public Long getPhotoId() {
		return photoId;
	}
	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}
	public String getPetPhotoName() {
		return petPhotoName;
	}
	public void setPetPhotoName(String petPhotoName) {
		this.petPhotoName = petPhotoName;
	}
	public byte[] getPetPhotoData() {
		return petPhotoData;
	}
	public void setPetPhotoData(byte[] petPhotoData) {
		this.petPhotoData = petPhotoData;
	}
	public Long getPetId() {
		return petId;
	}
	public void setPetId(Long petId) {
		this.petId = petId;
	}
	
	
}