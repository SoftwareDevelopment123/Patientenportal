package de.patientenportal.entities.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import de.patientenportal.entities.Case;

@XmlRootElement (name="caseListResponse")
public class CaseListResponse {
	
	private String responseCode = null;
	private List<Case> responseList;

	public CaseListResponse(){
	}
	
	public CaseListResponse(List<Case> caseList){
		this.responseList = caseList;
	}
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	@XmlElementWrapper(name="cases")
	@XmlElement(name="case")
	public List<Case> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<Case> responseList) {
		this.responseList = responseList;
	}
}
