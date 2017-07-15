package de.patientenportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.CascadeType;
import static javax.persistence.GenerationType.IDENTITY;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "User", catalog = "patientenportal", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
@SuppressWarnings("static-access")
@XmlRootElement (name="user")
public class User {

	private int userID;
	private String username;
	//public static String username;
	private String password;
	private String email;
	private String lastname;
	private String firstname;
	private Date birthdate;
	private Gender gender;	
	private	Doctor doctor;
	private	Patient patient;
	private	Relative relative;
	private Address address;
	private Contact contact;
	private WebSession webSession;
	
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
	@Temporal(TemporalType.DATE)
	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
		
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER", length = 10)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@XmlTransient
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@XmlTransient
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@XmlTransient
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

	@PrimaryKeyJoinColumn
	@OneToOne(fetch = FetchType.LAZY)
	@XmlTransient
	public WebSession getWebSession() {
		return webSession;
	}

	public void setWebSession(WebSession webSession) {
		this.webSession = webSession;
	}

	
}
