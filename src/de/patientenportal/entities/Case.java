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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.List;

@Entity
@Table(name = "Case", catalog = "patientenportal")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "case")
public class Case {

	private int caseID;
	private String caseTitle;
	private String caseDescription;
	private boolean status;
	private List<VitalData> vitaldata;
	private Patient patient;
	private List<MedicalDoc> medicalDocs;
	private List<Doctor> doctors;
	private List<Medication> medication;
	private List<InstructionDoc> idoc;

	public Case() {
	}

	public Case(String title) {
		this.caseTitle = title;
	}

	public Case(String title, String desc) {
		this.caseTitle = title;
		this.caseDescription = desc;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CASE_ID", unique = true, nullable = false)
	public int getCaseID() {
		return caseID;
	}

	public void setCaseID(int caseID) {
		this.caseID = caseID;
	}

	@Column(name = "TITLE", length = 45, nullable = false)
	public String getTitle() {
		return caseTitle;
	}

	public void setTitle(String title) {
		this.caseTitle = title;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return caseDescription;
	}

	public void setDescription(String description) {
		this.caseDescription = description;
	}

	@Column(name = "STATUS")
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pcase")
	@XmlTransient
	public List<VitalData> getVitaldata() {
		return vitaldata;
	}

	public void setVitaldata(List<VitalData> vitaldata) {
		this.vitaldata = vitaldata;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_fk")
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@OneToMany(mappedBy = "pcase")
	@XmlTransient
	public List<MedicalDoc> getMedicalDocs() {
		return medicalDocs;
	}

	public void setMedicalDocs(List<MedicalDoc> medicalDocs) {
		this.medicalDocs = medicalDocs;
	}

	@ManyToMany
	@XmlElementWrapper(name = "doctors")
	@XmlElement(name = "doctor")
	@JoinTable(name = "case_doctor")
	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	@OneToMany(mappedBy = "pcase")
	@XmlTransient
	public List<Medication> getMedication() {
		return medication;
	}

	public void setMedication(List<Medication> medication) {
		this.medication = medication;
	}

	@ManyToMany(mappedBy = "pcase")
	@XmlTransient
	public List<InstructionDoc> getIdoc() {
		return idoc;
	}

	public void setIdoc(List<InstructionDoc> idoc) {
		this.idoc = idoc;
	}
}
