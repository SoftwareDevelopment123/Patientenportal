package de.patientenportal.entities.response;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import de.patientenportal.entities.Patient;

@XmlRootElement (name="patientListResponse")
public class PatientListResponse {

	private String responseCode = null;
	private List<Patient> responseList;

	public PatientListResponse(){
	}
	
	public PatientListResponse(List<Patient> patientList){
		this.responseList = patientList;
	}
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	@XmlElementWrapper(name="patients")
	@XmlElement(name="patient")
	public List<Patient> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<Patient> responseList) {
		this.responseList = responseList;
	}
}
