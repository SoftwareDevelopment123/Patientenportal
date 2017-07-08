package de.patientenportal.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.List;

@Entity
@Table(name = "Relative", catalog = "patientenportal")
@XmlAccessorType(XmlAccessType.PROPERTY)
//@XmlRootElement (name="relative")

//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@XmlDiscriminatorValue("RELATIVE")

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
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="relatives") 
	@XmlTransient
	//@XmlInverseReference(mappedBy = "relatives")
	//@XmlElementWrapper(name="patients")
	//@XmlElement(name="patient")
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

}
