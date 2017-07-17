package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "Medication", catalog = "patientenportal")
@XmlRootElement (name="medication")
public class Medication {

	private int medicationID;
	private Medicine medicine;
	private String dosage;
	private String duration;
	private Doctor prescribedBy;
	private Case pcase;
	
	public Medication(){
	}

	public Medication(Medicine medicine, String dosage, String duration, Doctor prescribedBy, Case pcase) {
		this.medicine = medicine;
		this.dosage = dosage;
		this.duration = duration;
		this.prescribedBy = prescribedBy;
		this.pcase = pcase;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MEDICATION_ID", unique = true, nullable = false)
	public int getMedicationID() {
		return medicationID;
	}
	public void setMedicationID(int medicationID) {
		this.medicationID = medicationID;
	}

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="medicine_fk")
	public Medicine getMedicine() {
		return medicine;
	}
	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	@Column(name = "DOSAGE", length = 20)
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	@Column(name = "NAME", length = 60)
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="doctor_fk", nullable=false)
	public Doctor getPrescribedBy() {
		return prescribedBy;
	}
	public void setPrescribedBy(Doctor prescribedBy) {
		this.prescribedBy = prescribedBy;
	}

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="case_fk", nullable=false)
	@XmlTransient
	public Case getPcase() {
		return pcase;
	}
	public void setPcase(Case pcase) {
		this.pcase = pcase;
	}

}