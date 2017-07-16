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
import de.patientenportal.entities.Rights;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RightsDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.CaseWS")
public class CaseWSImpl implements CaseWS {

	/**
	 * <b>Einzelnen Fall vollständig abrufen</b><br>
	 * Der Patient kann nur seine eigenen Fälle über diese Methode aufrufen.<br>
	 * Bei Doktoren und Verwandten wird geprüft, ob man beim angefragten Fall Leserechte hat.<br>
	 *  
	 * Zugriffsbeschränkung: <code>Patient, Doctor, Relative</code>
	 * 
	 * @param Accessor mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>Case</code> mit dem angefragten Fall
	 */
	@Transactional
	public Case getCase(Accessor accessor) {
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
		ActiveRole role = AuthenticationWSImpl.getActiveRole(token);
		
		if (role == ActiveRole.Patient){
			String response = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
			if (response != null) {
				System.err.println(response);
				return null;
			}
			else {
				Case pcase = new Case();
				Patient patient = new Patient();
					try {
						User user = AuthenticationWSImpl.getUserByToken(token);
						patient = UserDAO.getUser(user.getUserId()).getPatient();
						pcase = CaseDAO.getCase(id);
					}
					catch (Exception e) {	System.err.println("Error: " + e);		return null;}
					if (pcase.getPatient().getPatientID() != patient.getPatientID()){
											System.err.println("Access denied");	return null;}
					return pcase;
			}
		}
		
		if (role != ActiveRole.Patient){
			String response = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.ReadCase);
			if (response != null) {
				System.err.println(response);
				return null;
			}
			else {
				Case pcase = new Case();
					try {
						pcase = CaseDAO.getCase(id);
					}
					catch (Exception e) {	System.err.println("Error: " + e);		return null;}
				return pcase;
			}
		}
		
		else {
			System.err.println("You should not have gotten here.");
			return null;
		}
	}

	/**
	 * <b>Alle Fälle eines Patienten abrufen</b><br>
	 * Methode ist für den Patienten gedacht, um seine Fälle einzusehen.<br>
	 * Dementsprechend wird nur das Token benötigt, aus dem dann die ihm zugehörige ID abgerufen wird.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Patient</code>
	 * 
	 * @param Accessor mit <code>String</code> token und <code>boolean</code> status
	 * @return <code>CaseListResponse</code> mit allen Fällen mit entsprechendem Status
	 */
	@Transactional
	public CaseListResponse getCases(Accessor accessor) {
		CaseListResponse response = new CaseListResponse();
		String token;
		boolean status = true;
		
		try {
			token = (String) accessor.getToken();
			status = (boolean) accessor.getObject();}
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
			Patient patient = new Patient();	
				try {
					User user = AuthenticationWSImpl.getUserByToken(token);
					patient = UserDAO.getUser(user.getUserId()).getPatient();

					List<Case> caselist1 = PatientDAO.getPatient(patient.getPatientID()).getCases();
					List<Case> caselist2 = new ArrayList<Case>();
						for (Case c : caselist1){
							caselist2.add(CaseDAO.getCase(c.getCaseID()));
						}
				
						response.setResponseCode("success");

						List <Case> rlist = new ArrayList<Case>();
						for (Case c : caselist2) {
							if (c.isStatus() == status) {
								rlist.add(c);
							}
						}
					response.setResponseList(rlist);
				}
				catch (Exception e) {
					System.err.println("Access failed");
					response.setResponseCode("Error: " + e);}
			return response;
		}
	}

	/**
	 * <b>Neuen Fall anlegen</b><br>
	 * Der erstellende Doktor bekommt direkt Lese- und Schreibrechte im erstellten Fall und
	 * wird der Doktorliste des Falls hinzugefügt. Hier wird sichergestellt, dass auch mitgegebene Doktoren
	 * dem Fall hinzugefügt werden. Diese bekommen aber nicht automatisch Lese- und Schreibrechte.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 *
	 * @param Accessor mit <code>String</code> token und dem zu erstellenden <code>Case</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String createCase(Accessor accessor) {
		Case pcase = new Case();
		String token;
		
		try {
			pcase = (Case) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (pcase.getTitle()	== null)	{return "Bitte einen Titel angeben.";}
		if (pcase.getPatient()	== null)	{return "Kein Patient angegeben.";}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		else { 
			String response = null;
			try {
				User creatinguser = AuthenticationWSImpl.getUserByToken(token);
				Doctor creatingdoctor = UserDAO.getUser(creatinguser.getUserId()).getDoctor();
				List<Doctor> doctors = pcase.getDoctors();
				
				boolean doublecheck = false;	
					for (Doctor d : doctors) {
						if (d.getDoctorID() == creatingdoctor.getDoctorID()){
							doublecheck = true;
						}
					}
				if (doublecheck == false){
					doctors.add(creatingdoctor);
				}

				pcase.setDoctors(doctors);
				CaseDAO.createCase(pcase);
				
				Rights right = new Rights(pcase, creatingdoctor,null,true,true);
				response = RightsDAO.createRight(right);
			} catch (Exception e) {
				System.err.println("Error: " + e); return "Error: " + e;
			}
			return response;
		}
	}

	/**
	 * <b>Fall löschen</b><br>
	 * Fälle sollten eigentlich nicht gelöscht, sondern nur geschlossen werden.<br>
	 * Zur Vollständigkeit ist diese Methode trotzdem vorhanden, die Berechtigung für den Doctor
	 * kann ggf. noch entfernt werden.
	 * 
	 * Zugriffsbeschränkung: <code>Doctor</code> (unter Vorbehalt) mit Schreibrecht beim Fall
	 * 
	 * @param Accessor mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String deleteCase(Accessor accessor) {
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
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}

		else{
			String response = null;
			try {response = CaseDAO.deleteCase(id);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	/**
	 * <b>Fall updaten</b><br>
	 * Hier ist zu beachten, dass möglichst eine Vollständige <code>Case</code>-Entity mitgegeben
	 * werden sollte.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor</code> mit Schreibrecht beim Fall
	 *
	 * @param Accessor mit <code>String</code> token und dem zu ändernden <code>Case</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updateCase(Accessor accessor) {
		Case pcase = new Case();
		String token;
		
		try {
			pcase = (Case) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		accessor.setObject(pcase.getCaseID());
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		else {
		String response = null;
		try {response = CaseDAO.updateCase(pcase);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}
}