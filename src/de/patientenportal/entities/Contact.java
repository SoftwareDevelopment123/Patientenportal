package de.patientenportal.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;
//import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "Contact", catalog = "patientenportal")
public class Contact {

	private int contactID;
	private String phone;
	private String mobile;
	private String email;
	
	public Contact() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
//	@GenericGenerator(name = "contact", strategy = "increment")
//	@GeneratedValue(generator = "contact")
	
	@Column(name = "CONTACT_ID", unique = true, nullable = false)
	public int getContactID() {
		return contactID;
	}

	public void setContactID(int contactID) {
		this.contactID = contactID;
	}

	@Column(name = "PHONE", length = 15)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "MOBILE", length = 15)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "EMAIL", length = 60)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}