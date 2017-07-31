package de.patientenportal.entities.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import de.patientenportal.entities.Rights;

@XmlRootElement(name = "rightsListResponse")
public class RightsListResponse extends ListResponse {

	private List<Rights> responseList;

	public RightsListResponse() {
	}

	public RightsListResponse(List<Rights> rightsList) {
		this.responseList = rightsList;
	}

	@XmlElementWrapper(name = "rights")
	@XmlElement(name = "right")
	public List<Rights> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<Rights> responseList) {
		this.responseList = responseList;
	}
}