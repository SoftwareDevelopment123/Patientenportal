package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Doctor", catalog = "patientenportal")
public class Doctor {

	@GeneratedValue
	private int doctorID;
	private String specialization;
	
	public Doctor() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	
	@Column(name = "DOCTOR_ID", unique = true, nullable = false)
	public int getDoctor_id() {
		return doctorID;
	}

	public void setDoctor_id(int doctorID) {
		this.doctorID = doctorID;
	}

	@Column(name = "SPECIALIZATION", length = 45)
	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	
	
}