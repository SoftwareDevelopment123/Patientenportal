package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "InstructionDoc", catalog = "patientenportal")
public class InstructionDoc extends Document {
	
	private int instructionDocID;
	private String instructionType;
	
	public InstructionDoc(){
	}
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "INSTRUCTION_ID", unique = true, nullable = false)
	public int getInstructionDocID() {
		return instructionDocID;
	}
	
	
	public void setInstructionDocID(int instructionDocID) {
		this.instructionDocID = instructionDocID;
	}
	
	@Column(name = "INSTRUCTION_TYPE", length = 40)
	public String getInstructionType() {
		return instructionType;
	}
	
	
	public void setInstructionType(String instructionType) {
		this.instructionType = instructionType;
	}
	

}
