package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Patient", catalog = "patientenportal")
public class Patient {

	@GeneratedValue
	private int patientID;
	private String bloodtype;
	//private Insurance insurance;			Verknüpfung fehlt noch
	//private List <Relative> relatives;	Verknüpfung fehlt noch
	//private List <Case> cases;			Verknüpfung fehlt noch
	//private List <MDoc> Mdoc;				Verknüpfung fehlt noch
	//Rückverknüpfung zum User			 	Verknüpfung fehlt noch
	
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
	
}