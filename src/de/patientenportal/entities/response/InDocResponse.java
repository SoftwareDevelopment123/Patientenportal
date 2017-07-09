package de.patientenportal.entities.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import de.patientenportal.entities.InstructionDoc;

@XmlRootElement (name="InDocListResponse")
public class InDocResponse {
	private String responseCode = null;
	private List<InstructionDoc> responseList;

	public InDocResponse(){
	}
	public InDocResponse (List<InstructionDoc> indoclist){
		this.responseList = indoclist;
	}
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	@XmlElementWrapper(name="instructiondocuments")
	@XmlElement(name="InstructionDocument")
	public List<InstructionDoc> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<InstructionDoc> responseList) {
		this.responseList = responseList;
	}


}
