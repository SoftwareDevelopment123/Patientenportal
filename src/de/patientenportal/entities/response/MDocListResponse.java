package de.patientenportal.entities.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import de.patientenportal.entities.MedicalDoc;

@XmlRootElement (name="mDocListResponse")
public class MDocListResponse extends ListResponse {

		private List<MedicalDoc> responseList;

		public MDocListResponse(){
		}
		public MDocListResponse(List<MedicalDoc> mdoclist){
			this.responseList = mdoclist;
		}
				
		@XmlElementWrapper(name="medicalDocuments")
		@XmlElement(name="medicalDocument")
		public List<MedicalDoc> getResponseList() {
			return responseList;
		}
		public void setResponseList(List<MedicalDoc> responseList) {
			this.responseList = responseList;
		}
}

