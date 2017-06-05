package de.patientenportal.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PatientCase", catalog = "patientenportal")
public class PatientCase {

	
	private int caseID;
	private String description;
	private boolean status;
	private Set<VitalData> vitaldatas = new HashSet<VitalData>(0);
	
	//Standardkosntruktor
	public PatientCase(){
	}
	
	//Konstruktor
	public PatientCase(String description, boolean status){ //,Set<VitalData> vitaldatas){
		this.description = description;
		this.status = status;
	//	this.vitaldatas = vitaldatas;
	}
	// Getter und Setter
	@Id
	@GenericGenerator(name = "case", strategy = "increment")
	@GeneratedValue(generator = "case")
	@Column(name = "CaseID", unique = true, nullable = false)
	public int getCaseID() {
		return caseID;
	}
	public void setCaseID(int caseID) {
		this.caseID = caseID;
	}
	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	
	
	
	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn(name="vitaldata_fk")
	//@JoinTable(name = "PatienCase_VitalData")
	public Set<VitalData> getVitaldatas() {
		return vitaldatas;
	}

	public void setVitaldatas(Set<VitalData> vitaldatas) {
		this.vitaldatas = vitaldatas;
	}
}
