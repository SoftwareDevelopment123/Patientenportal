package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.CascadeType;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "User", catalog = "patientenportal", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
@SuppressWarnings("static-access")
public class User {

	private int userID;
	public static String username;			//für die Criteria geändert ( auf Fehler überprüfen )
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
	
	public User(String username, String password, String firstname, String lastname){
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
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

	@Column(name = "BIRTHDATE", length = 15)
	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	@Column(name = "GENDER", length = 10)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	public Relative getRelative() {
		return relative;
	}

	public void setRelative(Relative relative) {
		this.relative = relative;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

}
