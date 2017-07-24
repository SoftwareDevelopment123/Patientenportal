package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.PatientListResponse;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;


@WebService (endpointInterface = "de.patientenportal.services.PatientWS")
public class PatientWSImpl implements PatientWS {
	
	//TODO Überprüfen ob Relative auch wirklich Verwandter des Patienten ist?
	/**
	 * <b>Patienten abrufen</b><br>
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> patientID 
	 * @return patient
	 */
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
		
		Patient patient = new Patient();
		try { patient = PatientDAO.getPatient(id); }
		catch (Exception e) {System.out.println("Error: " + e);}
		return patient;
		
	}

	/**
	 * <b>Alle zugeordneten Patienten zu einem Verwandten abrufen</b><br>
	 * 
	 * @param accessor mit <code>String</code> token 
	 * @return <code>PatientListResponse</code> mit den dem Verwandten zugeordneten Patienten.
	 */
	@Transactional
	public PatientListResponse getPatientsByR(Accessor accessor) {
		PatientListResponse response = new PatientListResponse();
		int relativeId;
		String token;
		
		try {
			token = (String) accessor.getToken();
		}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Relative,ActiveRole.Patient);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.ReadCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			return null;
		}

		else{
			try {
			//
			relativeId  = AuthenticationWSImpl.getUserByToken(token).getRelative().getRelativeID();

			List<Patient> rlist = RelativeDAO.getRelative(relativeId).getPatients();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}
}
