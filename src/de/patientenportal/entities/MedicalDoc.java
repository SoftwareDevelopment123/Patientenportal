package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MedicalDoc", catalog = "patientenportal")
public class MedicalDoc {

	private int medDocID;
	public String mDocTitle;
	public String mDocDescription;
	private Doctor createdBy;
	private Patient patient;
	private Case pcase;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MDOC_ID", unique = true, nullable = false)
	public int getMedDocID() {
		return medDocID;
	}
	public void setMedDocID(int medDocID) {
		this.medDocID = medDocID;
	}

	@Column(name = "TITLE", length = 20)
	public String getTitle() {
		return mDocTitle;
	}
	public void setTitle(String title) {
		this.mDocTitle = title;
	}
	
	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return mDocDescription;
	}
	public void setDescription(String description) {
		this.mDocDescription = description;
	}	
	
	@ManyToOne
	@JoinColumn(name="doctor_fk")
	public Doctor getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Doctor createdBy) {
		this.createdBy = createdBy;
	}

	@ManyToOne
	@JoinColumn(name="patient_fk"/*, nullable=false*/)		// Testen
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@ManyToOne
	@JoinColumn(name="case_fk")
	public Case getPcase() {
		return pcase;
	}
	public void setPcase(Case pcase) {
		this.pcase = pcase;
	}

	
}
