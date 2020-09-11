package com.petstore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pet {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long petId;
	private String petName;
	private enum status {
		AVAILABLE, PENDING, SOLD;
	}
	private status petStatus;
	
	public long getPetId() {
		return petId;
	}
	
	public void setPetId(long petId) {
		this.petId = petId;
	}
	
	public String getPetName() {
		return petName;
	}
	
	public void setPetName(String petName) {
		this.petName = petName;
	}
	
	public status getPetStatus() {
		return petStatus;
	}
	
	public void setPetStatus(status petStatus) {
		this.petStatus = petStatus;
	}
}
