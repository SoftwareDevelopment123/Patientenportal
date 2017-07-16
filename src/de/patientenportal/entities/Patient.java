package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import static javax.persistence.GenerationType.IDENTITY;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Patient", catalog = "patientenportal")
@XmlRootElement (name="patient")
@XmlAccessorType(XmlAccessType.PROPERTY)

public class Patient {

	private int patientID;
	private String bloodtype;
	private User user;
	private List <Relative> relatives = new ArrayList<Relative>();
	private Insurance insurance;
	private List <Case> cases;
	private List <MedicalDoc> medicalDocs;
	
	public Patient() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)	
	@Column(name = "PATIENT_ID", unique = true, nullable = false)
	public int getPatientID() {
		return patientID;
	}
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	
	@Column(name = "BLOODTYPE", length = 3)
	public String getBloodtype() {
		return bloodtype;
	}
	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}
	
	@OneToOne(mappedBy = "patient")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name="patient_relative")
	@XmlTransient
	public List<Relative> getRelatives() {
		return relatives;
	}
	public void setRelatives(List<Relative> relatives) {
		this.relatives = relatives;
	}

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name="insurance_fk")
	public Insurance getInsurance() {
		return insurance;
	}
	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	
	@OneToMany (mappedBy = "patient", fetch = FetchType.LAZY)
	@XmlTransient
	public List<Case> getCases() {
		return cases;
	}
	public void setCases(List<Case> cases) {
		this.cases = cases;
	}

	@OneToMany (mappedBy = "patient")
	@XmlTransient
	public List<MedicalDoc> getMedicalDocs() {
		return medicalDocs;
	}
	public void setMedicalDocs(List<MedicalDoc> medicalDocs) {
		this.medicalDocs = medicalDocs;
	}

}