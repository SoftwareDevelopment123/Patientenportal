package de.patientenportal.entities.response;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import de.patientenportal.entities.Medication;

@XmlRootElement (name="medicationListResponse")
public class MedicationListResponse extends ListResponse {
	
	private List<Medication> responseList;

	public MedicationListResponse(){
	}
	
	public MedicationListResponse(List<Medication> caseList){
		this.responseList = caseList;
	}
		
	@XmlElementWrapper(name="medications")
	@XmlElement(name="medication")
	public List<Medication> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<Medication> responseList) {
		this.responseList = responseList;
	}
}