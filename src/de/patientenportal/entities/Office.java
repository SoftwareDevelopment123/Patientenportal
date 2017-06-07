package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;


@Entity
@Table(name = "Office", catalog = "patientenportal")
public class Office {
	
	
	private int officeID;
	private String name;
	private Contact contact;
	private Address address;
	//Verknüpfung zu private Doctor doctor;
	
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

	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	

}
