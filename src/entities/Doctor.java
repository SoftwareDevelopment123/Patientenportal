package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class Doctor extends User {

	public Doctor() {
	}

	//Eigenschaften
	
	@Id
	@GeneratedValue
	@Column(name = "DOCTOR_ID")
	private int doctor_id;
		
	@Column(name = "PROFESSION")
	private String profession;

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
}
