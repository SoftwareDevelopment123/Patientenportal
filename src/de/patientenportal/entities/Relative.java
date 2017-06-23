package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;
import java.util.List;

@Entity
@Table(name = "Relative", catalog = "patientenportal")
public class Relative {
	
	private int relativeID;
	private User user;
	private List<Patient> patients;
	
	public Relative(){
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "RELATIVE_ID", unique = true, nullable = false)
	public int getRelativeID() {
		return relativeID;
	}
	public void setRelativeID(int relativeID) {
		this.relativeID = relativeID;
	}

	@OneToOne(mappedBy = "relative")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToMany(mappedBy="relatives")
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

}
