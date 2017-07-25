package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "InstructionDoc", catalog = "patientenportal")
@XmlRootElement (name="instructiondoc")
public class InstructionDoc {
	
	private int instructionDocID;
	public String title;
	public String description;
	private String instructionType;
	private String fileType;
	private List<Case> cases;

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
	
	@Column(name = "TITLE", length = 30)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}		
	
	@Column(name = "INSTRUCTION_TYPE", length = 40)
	public String getInstructionType() {
		return instructionType;
	}
	public void setInstructionType(String instructionType) {
		this.instructionType = instructionType;
	}
	
	@Column(name = "FILE_TYPE", length = 10)
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@ManyToMany
	@JoinTable(name="idoc_case")
	public List<Case> getPcase() {
		return cases;
	}
	public void setPcase(List<Case> cases) {
		this.cases = cases;
	}
}