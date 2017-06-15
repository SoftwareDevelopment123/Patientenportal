package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Rights", catalog = "patientenportal")
public class Rights {

	private int rightID;
	private Case pcase;
	private Doctor doctor;
	private Relative relative;
	private boolean rRight;
	private boolean wRight;

	public Rights(){
	}
	
	public Rights(Case pcase, Doctor doctor, Relative relative, boolean rRight, boolean wRight){
		this.pcase = pcase;
		this.doctor = doctor;
		this.relative = relative;
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

	@ManyToOne
	public Case getPcase() {
		return pcase;
	}
	public void setPcase(Case pcase) {
		this.pcase = pcase;
	}

	@OneToOne
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@OneToOne
	public Relative getRelative() {
		return relative;
	}
	public void setRelative(Relative relative) {
		this.relative = relative;
	}

	@Column(name = "RRIGHT")
	public boolean isrRight() {
		return rRight;
	}
	public void setrRight(boolean rRight) {
		this.rRight = rRight;
	}

	@Column(name = "WRIGHT")
	public boolean iswRight() {
		return wRight;
	}
	public void setwRight(boolean wRight) {
		this.wRight = wRight;
	}

}
