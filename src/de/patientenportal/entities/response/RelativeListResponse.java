package de.patientenportal.entities.response;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import de.patientenportal.entities.Relative;

@XmlRootElement(name = "relativeListResponse")
public class RelativeListResponse extends ListResponse {

	private List<Relative> responseList;

	public RelativeListResponse() {
	}

	public RelativeListResponse(List<Relative> relativeList) {
		this.responseList = relativeList;
	}

	@XmlElementWrapper(name = "relatives")
	@XmlElement(name = "relative")
	public List<Relative> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<Relative> responseList) {
		this.responseList = responseList;
	}
}