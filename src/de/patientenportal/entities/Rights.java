package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Rights", catalog = "patientenportal")
public class Rights {

	private int rightID;
	private int doctorID;
	private int relativeID;
	private boolean rRight;
	private boolean wRight;
	
	public Rights(){
	}
	
	public Rights(int doctorID, int relativeID, boolean rRight, boolean wRight){
		this.doctorID = doctorID;
		this.relativeID = relativeID;
		this.wRight = wRight;
		this.rRight = rRight;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "RIGHT_ID", unique = true, nullable = false)
	public int getRightID() {
		return rightID;
	}
	public void setRightID(int rightID) {
		this.rightID = rightID;
	}

	public int getDoctorID() {
		return doctorID;
	}
	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}

	public int getRelativeID() {
		return relativeID;
	}
	public void setRelativeID(int relativeID) {
		this.relativeID = relativeID;
	}

	public boolean isrRight() {
		return rRight;
	}
	public void setrRight(boolean rRight) {
		this.rRight = rRight;
	}

	public boolean iswRight() {
		return wRight;
	}
	public void setwRight(boolean wRight) {
		this.wRight = wRight;
	}

}
