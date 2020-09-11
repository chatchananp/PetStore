package com.petstore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PetPhoto {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long photoId;
	
	public long getPhotoId() {
		return photoId;
	}
	
	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}
}
