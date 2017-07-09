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
		String token;
		
		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		}
		catch (Exception e) 	{System.err.println("Invalid access");	return null;}
		if (id == 0) 			{System.err.println("Id null");			return null;}
		if (token == null) 		{System.err.println("No token");		return null;}
		
		AuthenticationWSImpl auth = new AuthenticationWSImpl();
		
		if (auth.authenticateToken(token) == false) {System.err.println("Invalid token"); return null;}
		// + else if authorizeToken(token) != 2 {error}
		
		Patient patient = new Patient();
		try { patient = PatientDAO.getPatient(id); }
		catch (Exception e) {System.out.println("Error: " + e);}
		return patient;
		
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
