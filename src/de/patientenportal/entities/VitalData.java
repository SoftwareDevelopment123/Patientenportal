package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "VitalData", catalog = "patientenportal")
public class VitalData  {

	private int vitalDataID;
	private String timestamp;
	private Double value;
	private VitalDataType vitalDataType;
	private Case pcase;

	//Standardkonstruktor
	public VitalData(){
	}
	
	//Konstruktor
	public VitalData(String timestamp,Double value,VitalDataType vitalDataType, Case pcase){
		this.timestamp = timestamp;
		this.value = value;
		this.vitalDataType =vitalDataType;
		this.pcase = pcase;
	}

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "VITALDATA_ID", unique = true, nullable = false)
	public int getVitalDataID() {
		return vitalDataID;
	}
	public void setVitalDataID(int vitalDataID) {
		this.vitalDataID = vitalDataID;
	}
	
	@Column(name = "TIMESTAMP", length = 45)
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@Column(name = "VALUE", length = 45)
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE")
	public VitalDataType getVitalDataType() {
		return vitalDataType;
	}
	public void setVitalDataType(VitalDataType vitalDataType) {
		this.vitalDataType = vitalDataType;
	}
	
	@ManyToOne
	@JoinColumn(name="case_fk")
	public Case getPcase() {
		return pcase;
	}
	public void setPcase(Case pcase) {
		this.pcase = pcase;
	}

}
