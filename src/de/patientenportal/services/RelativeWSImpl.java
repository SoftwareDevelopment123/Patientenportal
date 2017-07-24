package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RelativeListResponse;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;

@WebService (endpointInterface = "de.patientenportal.services.RelativeWS")
public class RelativeWSImpl implements RelativeWS {
	
	/**
	 * <b>Einen Verwandten zu einem Patienten abrufen</b><br>
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> relativID
	 * @return <code>Relative</code> 
	 */
	@Transactional
	public Relative getRelative(Accessor accessor) {
		int id;
		String token;
		
		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
			}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			return null;
		}
		
		else{
			Relative relative = new Relative();
			try {relative = RelativeDAO.getRelative(id);}
			catch (Exception e) {System.err.println("Error: " + e);}
		return relative;
		}
	}

	/**
	 * <b>Alle Verwandten zu einem Patienten abrufen</b><br>
	 * 
	 * Zugriffsbeschränkung: Doctor, Relative mit Leserecht beim betroffenen Fall<br>
	 * (Patienten können ohnehin ihre Fälle vollständig einsehen - siehe CaseWS) 
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> patientID
	 * @return <code>RelativeListResponse</code> mit allen Verwandten und Erfolgsmeldung oder Fehlermeldung.
	 */
	@Transactional
	public RelativeListResponse getRelativesByP(Accessor accessor) {
		RelativeListResponse response = new RelativeListResponse();
		int id;
		String token;
		
		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			response.setResponseCode(authResponse);
			return response;
		}
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
