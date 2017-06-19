package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

@Entity
@Table(name = "Insurance", catalog = "patientenportal")
public class Insurance {
	
	private int insuranceID;
	private int insuranceNr;
	private String name;
	private List<Patient> patient;
	
	public Insurance(){
	
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "INSURANCE_ID", unique = true, nullable = false)
	public int getInsuranceID() {
		return insuranceID;
	}

	public void setInsuranceID(int insuranceID) {
		this.insuranceID = insuranceID;
	}
	
	@Column(name = "INSURANCENR", length = 30)
	public int getInsuranceNr() {
		return insuranceNr;
	}

	public void setInsuranceNr(int insuranceNr) {
		this.insuranceNr = insuranceNr;
	}
	
	@Column(name = "NAME", length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany (fetch = FetchType.LAZY)
	@JoinColumn(name="insurance_patient_fk")
	public List<Patient> getPatient() {
		return patient;
	}

	public void setPatient(List<Patient> patient) {
		this.patient = patient;
	}

	
	
	
	
}
