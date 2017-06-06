package de.patientenportal.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "VitalData", catalog = "patientenportal")
public class VitalData  {
	
	
	
	private int vitalDataID;
	private String timestamp;
	private Double value;
	private VitalDataType vitalDataType;
	
	

	
	//Standardkonstruktor
	public VitalData(){
	}
	//Konstruktor
	public VitalData(String timestamp,Double value,VitalDataType vitalDataType){
		this.timestamp = timestamp;
		this.value = value;
		this.vitalDataType =vitalDataType;
	}
	 // getters and setters...
	
	@Id
	@GenericGenerator(name = "vitalData", strategy = "increment")
	@GeneratedValue(generator = "vitalData")
	
	@Column(name = "VitalDataID", unique = true, nullable = false)
	public int getVitalDataID() {
		return vitalDataID;
	}
	public void setVitalDataID(int vitalDataID) {
		this.vitalDataID = vitalDataID;
	}
	
	@Column(name = "Timestamp", length = 45)
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@Column(name = "Value", length = 45)
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

	@Enumerated(EnumType.STRING)
	public VitalDataType getVitalDataType() {
		return vitalDataType;
	}
	public void setVitalDataType(VitalDataType vitalDataType) {
		this.vitalDataType = vitalDataType;
	}
	
	
	
}
