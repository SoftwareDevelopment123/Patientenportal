package de.patientenportal.services;

import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.PatientListResponse;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;

@WebService (endpointInterface = "de.patientenportal.services.PatientWS")
public class PatientWSImpl implements PatientWS {

	@Transactional
	public Patient getPatient(Accessor accessor) {
		int id;
		
		try { id = (int) accessor.getObject(); }
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		else{
			Patient patient = new Patient();
			try { patient = PatientDAO.getPatient(id); }
			catch (Exception e) {System.out.println("Error: " + e);}
		return patient;
		}
	}

	@Transactional
	public PatientListResponse getPatientsByR(Accessor accessor) {
		PatientListResponse response = new PatientListResponse();
		int id;
		
		try {id = (int) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		else{
			try {
			List<Patient> rlist = RelativeDAO.getRelative(id).getPatients();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}
}
