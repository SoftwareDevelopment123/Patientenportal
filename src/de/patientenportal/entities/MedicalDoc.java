package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MedicalDoc", catalog = "patientenportal")
public class MedicalDoc extends Document {

	private int medDocID;
	private Doctor createdBy;
	
	public MedicalDoc(){
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MEDDOC_ID", unique = true, nullable = false)
	public int getMedDocID() {
		return medDocID;
	}
	
	public void setMedDocID(int medDocID) {
		this.medDocID = medDocID;
	}
	
	@Column(name = "CREATED_BY")
	public Doctor getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(Doctor createdBy) {
		this.createdBy = createdBy;
	}
	
}
