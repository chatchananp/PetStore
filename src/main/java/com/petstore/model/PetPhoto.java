package com.petstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Photo")
public class PetPhoto {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name = "photo_id")
	private Long photoId;
	
	@Lob @Column(name = "photo_data")
	private byte[] petPhotoData;
	
//	@Column(name = "pet_id")
//	private Long petId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pet pet;
	
	public PetPhoto() {}
	
	public PetPhoto(byte[] petPhotoData, Pet pet) {
		this.petPhotoData = petPhotoData;
		this.pet = pet;
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

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
}
