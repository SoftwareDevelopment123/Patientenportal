package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Doctor", catalog = "patientenportal")
public class Doctor extends User {

	@GeneratedValue
	private int doctor_id;
	private String specialization;
	
	public Doctor() {
	}

	//Eigenschaften
	public Doctor(int doctorId, String specialization) {
		this.doctor_id = doctorId;
		this.specialization = specialization;
	}

	@Id
	@Column(name = "DoctorId", unique = true, nullable = false)
	public int getDoctor_id() {
		return doctor_id;
	}


	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	@Column(name = "specialization", length = 45)
	public String getSpecialization() {
		return specialization;
	}


	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	
	
}