package de.patientenportal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;
import de.patientenportal.persistence.RightsDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.AccessWS")
public class AccessWSImpl implements AccessWS {

	/**
	 * <b>Alle einsehbaren Fälle abrufen</b><br>
	 * Über das Token werden zu, ensprechenden user die Leserechte abgefragt und die einsehbaren Fälle angezeigt.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor, Relative</code>
	 * 
	 * @param Accessor mit <code>String</code> token und <code>boolean</code> status
	 * @return <code>CaseListResponse</code> mit allen Fällen mit entsprechendem Status
	 */
	@Transactional
	public CaseListResponse getRCases(Accessor accessor) {
		CaseListResponse response = new CaseListResponse();
		String token;
		boolean status = true;
		
		try {
			token = (String) accessor.getToken();
			status = (boolean) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); 	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			response.setResponseCode(authResponse);
			return response;
		}
		
		User user = AuthenticationWSImpl.getUserByToken(token);
		List<Case> caselist = new ArrayList<Case>();
		
		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Doctor){
			Doctor doctor = UserDAO.getUser(user.getUserId()).getDoctor();
			caselist = RightsDAO.getDocRCases(doctor.getDoctorID());
		}
		
		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Relative){
			Relative relative = UserDAO.getUser(user.getUserId()).getRelative();
			caselist = RightsDAO.getRelRCases(relative.getRelativeID());
		}

		List <Case> rlist = new ArrayList<Case>();
		for (Case c : caselist) {
			if (c.isStatus() == status) {
				rlist.add(c);
			}
		}
		response.setResponseCode("success");
		response.setResponseList(rlist);
		return response;
	}

	/**
	 * <b>Alle einsehbaren Fälle zu einem Patienten abrufen</b><br>
	 * Über das Token werden zu, ensprechenden user die Leserechte abgefragt und die einsehbaren Fälle angezeigt.<br>
	 * Dann werden nach der patientID die entsprechenden Fälle herausgefiltert.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor, Relative</code>
	 * 
	 * @param Accessor mit <code>String</code> token und <code>int</code> patientID
	 * @return <code>CaseListResponse</code> mit allen Fällen mit entsprechendem Status
	 */
	@Transactional
	public CaseListResponse getRPatientCases(Accessor accessor) {
		CaseListResponse response = new CaseListResponse();
		String token;
		int id;
		
		try {
			token = (String) accessor.getToken();
			id = (int) accessor.getObject();
		}
		catch (Exception e) {System.err.println("Invalid access"); 	return null;}
		if (id == 0)		{System.err.println("No Patient-ID");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			response.setResponseCode(authResponse);
			return response;
		}
		
		User user = AuthenticationWSImpl.getUserByToken(token);
		List<Case> caselist = new ArrayList<Case>();
		
		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Doctor){
			Doctor doctor = UserDAO.getUser(user.getUserId()).getDoctor();
			caselist = RightsDAO.getDocRCases(doctor.getDoctorID());
		}
		
		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Relative){
			Relative relative = UserDAO.getUser(user.getUserId()).getRelative();
			caselist = RightsDAO.getRelRCases(relative.getRelativeID());
		}

		List <Case> rlist = new ArrayList<Case>();
		for (Case c : caselist) {
			if (c.getPatient().getPatientID() == id) {
				rlist.add(c);
			}
		}
		response.setResponseCode("success");
		response.setResponseList(rlist);
		return response;
	}

	/**
	 * <b>Schreibrecht bei einem Fall abrufen</b><br>
	 * Über das Token und die caseID wird das Schreibrecht abgefragt. Dient nur zur Anzeige der Information.<br>
	 * Die Schreibrechtprüfung findet bei den entsprechenden Methoden individuell statt.
	 * 
	 * Zugriffsbeschränkung: <code>Doctor, Relative</code>
	 * 
	 * @param Accessor mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>boolean</code>
	 */
	@Transactional
	public boolean checkWRight(Accessor accessor) {
		String token;
		int id;
		
		try {
			token = (String) accessor.getToken();
			id = (int) accessor.getObject();
		}
		catch (Exception e) {System.err.println("Invalid access"); 	return false;}
		if (id == 0)		{System.err.println("No Patient-ID");	return false;}
		if (token == null) 	{System.err.println("No token");		return false;}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Relative);
		String response = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);
		if (response != null) {
			System.err.println(response);
			return false;
		}
		return true;
	}
}
