package de.patientenportal.services;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RelativeListResponse;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;

@WebService (endpointInterface = "de.patientenportal.services.RelativeWS")
public class RelativeWSImpl implements RelativeWS {
	
	@Transactional
	public Relative getRelative(Accessor accessor) {

		int id = (int) accessor.getObject();
		if (id == 0) {return null;}
		
		else{
			Relative relative = RelativeDAO.getRelative(id);

			return relative;
		}
	}

	@Transactional
	public RelativeListResponse getRelativesByP(int patientID) {
		
		if (patientID == 0) {return null;}
		
		else{
			RelativeListResponse response = new RelativeListResponse();
			try {
			List<Relative> rlist = PatientDAO.getPatient(patientID).getRelatives();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			}
			return response;
		}
	}

}
