package de.patientenportal.entities.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import de.patientenportal.entities.InstructionDoc;

@XmlRootElement(name = "inDocListResponse")
public class InDocListResponse extends ListResponse {

	private List<InstructionDoc> responseList;

	public InDocListResponse() {
	}

	public InDocListResponse(List<InstructionDoc> indoclist) {
		this.responseList = indoclist;
	}

	@XmlElementWrapper(name = "instructionDocuments")
	@XmlElement(name = "instructionDocument")
	public List<InstructionDoc> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<InstructionDoc> responseList) {
		this.responseList = responseList;
	}
}