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
import de.patientenportal.entities.Medication;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MedicationListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.MedicationDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.MedicationWS")
public class MedicationWSImpl implements MedicationWS {

	/**
	 * <b>Einem Behandlungsfall zugeordnete Medikationen abrufen</b><br>
	 * Über das Token wird überprüft, ob der User über die entsprechenden Leserechte verfügt.<br>
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>MedicationListResponse</code> mit der dem Fall zugeordneten Medikation
	 */
	@Transactional
	public MedicationListResponse getMedicationbyC(Accessor accessor) {
		MedicationListResponse response = new MedicationListResponse();
		int id;
		String token;
		
		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		}
		catch (Exception e) {System.err.println("Invalid access"); 	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (id == 0) 		{System.err.println("Id null"); 		return null;}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient, ActiveRole.Doctor, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.ReadCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			return null;
		}

		else{
			try {
			List<Medication> mlist =  CaseDAO.getCase(id).getMedication();
			List <Medication> medlist = new ArrayList<Medication>();
			for(Medication m: mlist){
				medlist.add(MedicationDAO.getMedication(m.getMedicationID()));
			}
			
				response.setResponseCode("success");
				response.setResponseList(medlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}

	/**
	 * <b>Alle, einem Patienten zugeordneten, Medikationen abrufen</b><br>
	 * Über das Token wird überprüft, ob der User über die entsprechenden Leserechte verfügt.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Patient</code>
	 *  
	 * @param accessor mit <code>String</code> token 
	 * @return <code>MedicationListResponse</code> mit der dem Patienten zugeordneten Medikation
	 */
	@Transactional
	public MedicationListResponse getMedicationbyP(Accessor accessor) {
		MedicationListResponse response = new MedicationListResponse();
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
			return null;
		}

		else{
			try {
				Patient patient = new Patient();
				List<Medication> mlist = new ArrayList<Medication>();
				User user = AuthenticationWSImpl.getUserByToken(token);
				patient = UserDAO.getUser(user.getUserId()).getPatient();
				List<Case> cList = PatientDAO.getPatient(patient.getPatientID()).getCases();
				
				for(Case c: cList){
					for(Medication m: c.getMedication()){
						mlist.add(m);
					}
				}
				response.setResponseCode("success");
				response.setResponseList(mlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}
	
	/**
	 * <b>Medikation hinzufügen</b><br>
	 * Über das Token wird überprüft, ob der Doktor über die entsprechenden Schreibrechte verfügt.<br>
	 * Außerdem wird der anlegende Doktor automatisch der Medikation hinzugefügt.
	 * 
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 * 
	 * @param accessor mit <code>String</code> token und den anzulegenden Medikation
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String createMedication(Accessor accessor) {
		Medication medication = new Medication();
		String token;
		
		try {
			medication = (Medication) accessor.getObject();
			token = (String) accessor.getToken();
		} 

		catch (Exception e) 							{return "Falscher Input";}
		if (token == null) 								{return "Kein Token angegeben";}
		if (medication.getMedicine()	== null)		{return "Bitte eine Medikament angeben.";}
		if (medication.getDosage()		== null)		{return "Keine Dosierungsempfehlung angegeben.";}
		if (medication.getDuration()	== null)		{return "Keine Verschreibungsdauer angegeben.";}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		accessor.setObject(medication.getPcase().getCaseID());
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		else { 
			String response = null;
			try {
				User creatinguser = AuthenticationWSImpl.getUserByToken(token);
				Doctor creatingdoctor = UserDAO.getUser(creatinguser.getUserId()).getDoctor();
				
				if (creatingdoctor != null){
				medication.setPrescribedBy(creatingdoctor);
				medication.setPcase(CaseDAO.getCase(id));
				response = MedicationDAO.createMedication(medication);
				}
				else return "Fehler";
			} catch (Exception e) {
				System.err.println("Error: " + e); return "Error: " + e;
			}
			return response;
		}
	}

	/**
	 * <b>Medikation löschen</b><br>
	 * Über das Token wird überprüft, ob der Doktor über die entsprechenden Schreibrechte verfügt.<br>
	 *
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> MedicationID der zu löschenden Medikation.
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String deleteMedication(Accessor accessor) {
		int id;
		String token;
		
		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		}
		catch (Exception e) {System.err.println("Invalid access"); 	return "Falscher Input";}
		if (token == null) 	{System.err.println("No token");		return "Kein Token angegeben";}
		if (id == 0) 		{System.err.println("Id null"); 		return "Keine ID angegeben";}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		accessor.setObject(MedicationDAO.getMedication(id).getPcase().getCaseID());
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}

		else{
			String response = null;
			try {response = MedicationDAO.deleteMedication(id);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	/**
	 * <b>Medikation ändern</b><br>
	 * Über das Token wird überprüft, ob der Doktor über die entsprechenden Schreibrechte verfügt.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor</code> 
	 * 
	 * @param accessor mit <code>String</code> token und der zu ändernden <code>Medication</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updateMedication(Accessor accessor) {
		Medication medication = new Medication();
		String token;
		
		try {
			medication = (Medication) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		accessor.setObject(medication.getPcase().getCaseID());
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		else {
		String response = null;
		try {response = MedicationDAO.updateMedication(medication);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}
}