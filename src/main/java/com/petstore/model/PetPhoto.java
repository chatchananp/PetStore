package com.petstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Photo")
public class PetPhoto {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name = "photo_id")
	private String photoId;
	
	@Lob @Column(name = "photo_data")
	private byte[] petPhotoData;
	
	@Column(name = "pet_id")
	private String petId;
	
	public PetPhoto() {}
	
	public PetPhoto(byte[] petPhotoData, String petId) {
		this.petPhotoData = petPhotoData;
		this.petId = petId;
	}
	
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
