package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
//import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "Patient", catalog = "patientenportal")
public class Patient {

	private int patientID;
	private String bloodtype;
	private User user;
	private List <Relative> relatives;
//	private List <Case> cases;				heute
	
	//private Insurance insurance;			Verknüpfung fehlt noch
	//private List <MDoc> Mdoc;				Verknüpfung fehlt noch
	
	public Patient() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	//@GenericGenerator(name = "patient", strategy = "increment")
	//@GeneratedValue(generator = "patient")
	
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
	
	@OneToOne(targetEntity = User.class)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToMany
	@JoinTable(name="patient_relative")
	public List<Relative> getRelatives() {
		return relatives;
	}
	public void setRelatives(List<Relative> relatives) {
		this.relatives = relatives;
	}
	
	
}