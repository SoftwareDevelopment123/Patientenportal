package de.patientenportal.entities.response;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.patientenportal.entities.VitalData;


@XmlRootElement (name="userListResponse")
public class VitalDataListResponse extends ListResponse{

	private List<VitalData> responseList;

	public VitalDataListResponse(){
	}
	
	public VitalDataListResponse(List<VitalData> vitalDatalist){
		this.responseList = vitalDatalist;
	}
		
	@XmlElement(name="vitalData")
	public List<VitalData> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<VitalData> responseList) {
		this.responseList = responseList;
	}
	
}

