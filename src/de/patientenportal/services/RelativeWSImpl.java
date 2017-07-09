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

		int id;
		try { id = (int) accessor.getObject(); }
		catch (Exception e) { System.out.println("Invalid access"); return null; }
		
		if (id == 0) {System.out.println("Id null"); return null;}
		
		else{
			Relative relative = new Relative();
			try { relative = RelativeDAO.getRelative(id); }
			catch (Exception e) { System.out.println("Error: " + e); }
		return relative;
		}
	}

	@Transactional
	public RelativeListResponse getRelativesByP(Accessor accessor) {
		RelativeListResponse response = new RelativeListResponse();
		
		int id;
		try { id = (int) accessor.getObject(); }
		catch (Exception e) { System.out.println("Invalid access"); return null; }
		
		if (id == 0) {System.out.println("Id null"); return null;}
		
		else{
			try {
			List<Relative> rlist = PatientDAO.getPatient(id).getRelatives();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}

}
