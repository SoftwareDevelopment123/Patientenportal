package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;
//import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "Doctor", catalog = "patientenportal")
public class Doctor {

	private int doctorID;
	private String specialization;
	private User user;
	//private Office office;			Verknüpfung fehlt noch
	//private List <Case> cases;		Verknüpfung fehlt noch
	
	public Doctor() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	//@GenericGenerator(name = "doctor", strategy = "increment")
	//@GeneratedValue(generator = "doctor")
	
	@Column(name = "DOCTOR_ID", unique = true, nullable = false)
	public int getDoctor_id() {
		return doctorID;
	}

	public void setDoctor_id(int doctorID) {
		this.doctorID = doctorID;
	}

	@Column(name = "SPECIALIZATION", length = 45)
	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	@OneToOne(targetEntity = User.class)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}