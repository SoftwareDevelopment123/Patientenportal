package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;
import java.util.List;

@Entity
@Table(name = "Doctor", catalog = "patientenportal")
public class Doctor {

	private int doctorID;
	private String specialization;
	private User user;
	private Office office;
	private List <Case> cases;
	
	public Doctor() {
	}
	
	public Doctor(String spec) {
		this.specialization = spec;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "DOCTOR_ID", unique = true, nullable = false)
	public int getDoctorID() {
		return doctorID;
	}
	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}

	@Column(name = "SPECIALIZATION", length = 45)
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	@OneToOne(mappedBy = "doctor")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="office_fk")
	public Office getOffice() {
		return office;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
	
	@ManyToMany(mappedBy="doctors")
	public List<Case> getCases() {
		return cases;
	}
	public void setCases(List<Case> cases) {
		this.cases = cases;
	}
}