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
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.DoctorListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.DoctorDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.DoctorWS")
public class DoctorWSImpl implements DoctorWS {

	/**
	 * <b>Doktor per ID abrufen</b><br>
	 * 
	 * Zugriffsbeschränkung: keine
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> doctorID
	 * @return <code>Doctor</code>
	 */
	@Transactional
	public Doctor getDoctor(Accessor accessor) {
		int id;
		String token;
		
		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		}
		catch (Exception e) {System.err.println("Invalid access"); 	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (id == 0) 		{System.err.println("Id null"); 		return null;}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Relative, ActiveRole.Patient);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			return null;
		}
		
		else{
			Doctor doctor = new Doctor();
			try { doctor = DoctorDAO.getDoctor(id); }
			catch (Exception e) {System.err.println("Error: " + e);
		}
		return doctor;
		}
	}

	/**
	 * <b>Alle Doktoren von einem Fall abrufen</b><br>
	 * 
	 * Zugriffsbeschränkung: Doctor, Relative mit Leserecht beim betroffenen Fall<br>
	 * (Patienten können ohnehin ihre Fälle vollständig einsehen - siehe CaseWS) 
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>DoctorListResponse</code>
	 */
	@Transactional
	public DoctorListResponse getDoctorsByC(Accessor accessor) {
		DoctorListResponse response = new DoctorListResponse();
		int id;
		String token;
		
		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		}
		catch (Exception e) {System.err.println("Invalid access"); 	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (id == 0) 		{System.err.println("Id null"); 		return null;}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.ReadCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			response.setResponseCode(authResponse);
			return response;
		}
		
		else{
			try {
			List<Doctor> rlist = CaseDAO.getCase(id).getDoctors();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}		
	}

	/**
	 * <b>Alle Doktoren eines Patienten abrufen</b><br>
	 * Über die CaseList des Patienten werden alle Doktoren abgerufen. Damit nur eigene Fälle abgerufen werden, wird der Patient
	 * direkt aus dem Token ermittelt. <br>
	 * Eine dritte for-Schleife sortiert dir finale Liste-sodass bei den Doktoren keine Dopplungen auftreten. <br>
	 * 
	 * Zugriffsbeschränkung: Patient
	 * 
	 * @param accessor mit <code>String</code> token
	 * @return <code>DoctorListResponse</code>
	 */
	@Transactional
	public DoctorListResponse getDoctorsByP(Accessor accessor) {
		DoctorListResponse response = new DoctorListResponse();
		String token;
		
		try {
			token = (String) accessor.getToken();
		}
		catch (Exception e) {System.err.println("Invalid access"); 	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			response.setResponseCode(authResponse);
			return response;
		}

		else{
			try {
			User user = AuthenticationWSImpl.getUserByToken(token);
			Patient patient = UserDAO.getUser(user.getUserId()).getPatient();
				
			List<Case> cases = PatientDAO.getPatient(patient.getPatientID()).getCases();
			List<Doctor> pDoctors = new ArrayList<Doctor>();
						
				for (Case c : cases) {
					List <Doctor> caselist = CaseDAO.getCase(c.getCaseID()).getDoctors();
				
					for (Doctor d : caselist){
						boolean alreadyInList = false;
		
						for (Doctor dfromlist : pDoctors){
							if (d.getDoctorID() == dfromlist.getDoctorID()){alreadyInList = true;}
						}
						if (alreadyInList == false){pDoctors.add(d);}
					}		
				}
			response.setResponseCode("success");
			response.setResponseList(pDoctors);
			
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}
}