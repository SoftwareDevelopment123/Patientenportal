package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import static javax.persistence.GenerationType.IDENTITY;
import java.util.List;


//import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Case", catalog = "patientenportal")
public class Case {

	private int caseID;
	private String title;
	private String description;
	private boolean status;
	private List<VitalData> vitaldata;
	private Patient patient;
	private List<MedicalDoc> medicalDocs;
	//private List<Medication> medication;			//noch nicht implementiert
	//private List<InstructionDoc> idoc;				//noch nicht implementiert
	//Verknüpfung Doctor
	
	public Case(){	
	}
	public Case(String title) {
		this.title = title;
	}
	public Case(String title, String desc) {
		this.title = title;
		this.description = desc;

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
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "STATUS"/*, nullable = false*/)
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	@OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="case_fk")					// noch ändern, sodass von VData aus gemapped wird
	public List<VitalData> getVitaldata() {
		return vitaldata;
	}
	public void setVitaldata(List<VitalData> vitaldata) {
		this.vitaldata = vitaldata;
	}

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="patient_fk")
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@OneToMany (mappedBy="pcase")
	public List<MedicalDoc> getMedicalDocs() {
		return medicalDocs;
	}
	public void setMedicalDocs(List<MedicalDoc> medicalDocs) {
		this.medicalDocs = medicalDocs;
	}

/*	public List<Medication> getMedication() {
		return medication;
	}
	public void setMedication(List<Medication> medication) {
		this.medication = medication;
	}

	public List<InstructionDoc> getIdoc() {
		return idoc;
	}
	public void setIdoc(List<InstructionDoc> idoc) {
		this.idoc = idoc;
	}*/
	
}
