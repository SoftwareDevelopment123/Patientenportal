package de.patientenportal.entities.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import de.patientenportal.entities.MedicalDoc;

@XmlRootElement (name="mDocListResponse")
public class MDocListResponse {

		private String responseCode = null;
		private List<MedicalDoc> responseList;

		public MDocListResponse(){
		}
		public MDocListResponse(List<MedicalDoc> mdoclist){
			this.responseList = mdoclist;
		}
		
		public String getResponseCode() {
			return responseCode;
		}
		public void setResponseCode(String responseCode) {
			this.responseCode = responseCode;
		}
		
		@XmlElementWrapper(name="medicaldocuments")
		@XmlElement(name="MedicalDocument")
		public List<MedicalDoc> getResponseList() {
			return responseList;
		}
		public void setResponseList(List<MedicalDoc> responseList) {
			this.responseList = responseList;
		}


}

