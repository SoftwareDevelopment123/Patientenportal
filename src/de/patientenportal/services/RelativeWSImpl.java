package de.patientenportal.services;

import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Relative;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;

@WebService (endpointInterface = "de.patientenportal.services.RelativeWS")
public class RelativeWSImpl implements RelativeWS {

	@Transactional
	public Relative getRelative(int relativeID) {

		if (relativeID == 0) {return null;}
		
		else{
			Relative relative = RelativeDAO.getRelative(relativeID);
			return relative;
		}
	}

	@Transactional
	public List<Relative> getRelativesByP(int patientID) {
		
		if (patientID == 0) {return null;}
		
		else{
			List<Relative> relatives = PatientDAO.getPatient(patientID).getRelatives();
			return relatives;
		}
	}

}
