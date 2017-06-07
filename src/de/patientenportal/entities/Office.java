package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "Office", catalog = "patientenportal")
public class Office {
	
	
	private int officeID;
	private String name;
	//Verknüpfung zu private Doctor doctor;
	//Verknüpfung zu private Contact contact;
	//VErknüpfung zu private Address address;
	
	public Office(){
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "OFFICE_ID", unique = true, nullable = false)
	public int getOfficeID() {
		return officeID;
	}

	public void setOfficeID(int officeID) {
		this.officeID = officeID;
	}

	@Column(name = "OFFICENAME", length = 40)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
