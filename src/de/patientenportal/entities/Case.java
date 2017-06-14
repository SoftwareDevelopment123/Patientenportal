package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
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
	private List<Rights> rights;					//noch nicht implementiert
	private List<Medication> medication;			//noch nicht implementiert
	private List<InstructionDoc> idoc;				//noch nicht implementiert
	private List<MedicalDoc> mdoc;					//noch nicht implementiert
	
	//Verknüpfung Doctor
	//Verknüpfung Patient
	
	public Case(){	
	}
	
	public Case(String title) {
		this.title = title;
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
	@JoinColumn(name="case_fk")
	public List<VitalData> getVitaldata() {
		return vitaldata;
	}
	public void setVitaldata(List<VitalData> vitaldata) {
		this.vitaldata = vitaldata;
	}

	@OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="case_fk")
	public List<Rights> getRights() {
		return rights;
	}
	public void setRights(List<Rights> rights) {
		this.rights = rights;
	}

	public List<Medication> getMedication() {
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
	}

	public List<MedicalDoc> getMdoc() {
		return mdoc;
	}
	public void setMdoc(List<MedicalDoc> mdoc) {
		this.mdoc = mdoc;
	}
	

	
}
