package de.patientenportal.services;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.response.RelativeListResponse;
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
	public RelativeListResponse getRelativesByP(int patientID) {
		
		if (patientID == 0) {return null;}
		
		else{
			List<Relative> rlist = PatientDAO.getPatient(patientID).getRelatives();

			RelativeListResponse response = new RelativeListResponse();
				response.setResponseCode("send me over");
				response.setResponseList(rlist);
			
			/*ArrayList<Relative> relatives = new ArrayList<Relative>();
				relatives.addAll(rlist);*/
			
			/*System.out.println("Impl-Side: " + relatives.size());
			for (Relative r : relatives){
				System.out.print(r.getRelativeID());
				System.out.print(" - " + r.getUser().getFirstname());
				System.out.println(" - " + r.getUser().getLastname());
			}*/	
				
			return response;
		}
	}

}
