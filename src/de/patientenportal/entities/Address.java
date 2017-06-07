package de.patientenportal.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;
//import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "Address", catalog = "patientenportal")
public class Address {

	private int addressID;
	private String postalCode;
	private String street;
	private String number;
	private String city;
	
	public Address() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
//	@GenericGenerator(name = "address", strategy = "increment")
//	@GeneratedValue(generator = "address")
	
	@Column(name = "ADDRESS_ID", unique = true, nullable = false)
	public int getAddressID() {
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	@Column(name = "POSTALCODE", length = 8)
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "STREET", length = 60)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "NUMBER", length = 6)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "CITY", length = 60)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}