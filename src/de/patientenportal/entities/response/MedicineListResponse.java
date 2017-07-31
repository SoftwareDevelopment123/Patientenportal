package de.patientenportal.entities.response;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import de.patientenportal.entities.Medicine;

@XmlRootElement(name = "medicineListResponse")
public class MedicineListResponse extends ListResponse {

	private List<Medicine> responseList;

	public MedicineListResponse() {
	}

	public MedicineListResponse(List<Medicine> responseList) {
		this.responseList = responseList;
	}

	@XmlElementWrapper(name = "medicines")
	@XmlElement(name = "medicine")
	public List<Medicine> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<Medicine> responseList) {
		this.responseList = responseList;
	}
}