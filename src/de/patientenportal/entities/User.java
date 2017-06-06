package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "User", catalog = "patientenportal", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
public class User /*implements java.io.Serializable*/ {

	private int userID;
	private String username;
	private String password;
	private String email;
	private String lastname;
	private String firstname;
	private String birthdate;
	private String gender;	
	private	Doctor doctor;
	private	Patient patient;
	private	Relative relative;
	private Address address;
	private Contact contact;

	
	public User() {
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	
	@Column(name = "USER_ID", unique = true, nullable = false)
	public int getUserId() {
		return this.userID;
	}

	public void setUserId(int userID) {
		this.userID = userID;
	}

	@Column(name = "USERNAME", length = 45, unique = true, nullable = false)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", length = 45, nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "EMAIL", length = 60)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "LASTNAME", length = 45)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "FIRSTNAME", length = 45)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	public Relative getRelative() {
		return relative;
	}

	public void setRelative(Relative relative) {
		this.relative = relative;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
}
