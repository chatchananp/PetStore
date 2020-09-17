package com.petstore.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.petstore.exception.Enum;

@Entity
@Table(name = "Pet")
public class Pet {

	@Id
	@Column(name = "pet_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long petId;
	
	@Column(name = "pet_name")
	@NotBlank(message = "Please insert pet name")
	private String petName;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet_id", referencedColumnName = "pet_id")
	private List<PetPhoto> petPhotos;

	public List<PetPhoto> getPetPhotos() {
		return petPhotos;
	}
	
	public enum Status {
		available, pending, sold
	}
	
	@Column(name = "pet_status")
	//@NotBlank(message = "Please insert status")
	@Enum(enumClass=Status.class, ignoreCase=true)
	private String petStatus;
	
	public Pet() {}
	
	public Pet(String petName, String petStatus) {
		this.petName = petName;
		this.petStatus = petStatus;
	}

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

	public String getPetStatus() {
		return petStatus;
	}
	public void setPetStatus(String petStatus) {
		this.petStatus = petStatus;
	}
}
