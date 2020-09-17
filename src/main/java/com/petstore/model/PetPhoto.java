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
	private Long photoId;
	
	@Lob @Column(name = "photo_data")
	private byte[] petPhotoData;
	
	@Column(name = "pet_id")
	private Long petId;
	
	public PetPhoto() {}
	
	public PetPhoto(byte[] petPhotoData, Long petId) {
		this.petPhotoData = petPhotoData;
		this.petId = petId;
	}
	
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

	public Long getPetId() {
		return petId;
	}

	public void setPetId(Long petId) {
		this.petId = petId;
	}
}
