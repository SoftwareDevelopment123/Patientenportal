package de.patientenportal.entities.response;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import de.patientenportal.entities.Doctor;

@XmlRootElement (name="doctorListResponse")
public class DoctorListResponse {

	private String responseCode = null;
	private List<Doctor> responseList;

	public DoctorListResponse(){
	}
	
	public DoctorListResponse(List<Doctor> relativeList){
		this.responseList = relativeList;
	}
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	@XmlElementWrapper(name="doctors")
	@XmlElement(name="doctor")
	public List<Doctor> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<Doctor> responseList) {
		this.responseList = responseList;
	}

}
