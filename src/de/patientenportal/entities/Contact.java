package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Contact", catalog = "patientenportal")
public class Contact {

	private int contactID;
	private int phone;
	private int mobile;
	private String email;
	
	public Contact() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	
	@Column(name = "CONTACT_ID", unique = true, nullable = false)
	public int getContactID() {
		return contactID;
	}

	public void setContactID(int contactID) {
		this.contactID = contactID;
	}

	@Column(name = "PHONE", length = 15)
	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	@Column(name = "MOBILE", length = 15)
	public int getMobile() {
		return mobile;
	}

	public void setMobile(int mobile) {
		this.mobile = mobile;
	}

	@Column(name = "EMAIL", length = 30)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}