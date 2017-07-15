package de.patientenportal.services;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
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

	/*
	 * Einzelnen Fall abrufen (wird theoretisch nicht benötigt)
	 * 
	 * Es wird explizit über das Token geprüft, ob der anfragende User auch Patient im angefragten Fall ist
	 * 
	 * Zugriffsbeschränkung: Patient ( noch einzurichten )
	 * Doktor und Relative sollen nur über ihre Zugriffsrechte (Rights) Zugang zu Fällen bekommen
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
		
		AuthenticationWSImpl auth = new AuthenticationWSImpl();
		if (auth.authenticateToken(token) == false){
							System.err.println("Invalid token"); 	return null;}
		else{
		Case pcase = new Case();
		Patient patient = new Patient();
			try {
				User user = auth.getUserByToken(token);
				patient = UserDAO.getUser(user.getUserId()).getPatient();
				pcase = CaseDAO.getCase(id);
			}
			catch (Exception e) {System.err.println("Error: " + e);}
		if (pcase.getPatient().getPatientID() != patient.getPatientID()){
							System.err.println("Access denied");	return null;}
		return pcase;
		}
	}

	/*
	 * Alle Fälle eines Patienten abrufen
	 * 
	 * Methode ist für den Patienten gedacht, um seine Fälle einzusehen.
	 * Dementsprechend wird nur das Token benötigt, aus dem dann die zugehörige ID abgerufen wird.
	 * So wird garantiert, dass der angemeldete User/Patient nur seine eigenen Fälle sehen kann.
	 * 
	 * Zugriffsbeschränkung: Patient  ( noch einzurichten )
	 * 
	 * Wegen der Initialisierung der Cases wird aus der ersten Caselist(1) nochmal per ID auf die CaseDAO
	 * zugegriffen. Alternativ müsste man im PatientDAO jeden Case noch mal getrennt initialisieren
	 */
	
	@Transactional
	public CaseListResponse getCases(Accessor accessor) {
		CaseListResponse response = new CaseListResponse();
		String token;
		boolean status;
		
		try {
			token = (String) accessor.getToken();
			status = (boolean) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); 	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		
		AuthenticationWSImpl auth = new AuthenticationWSImpl();
		if (auth.authenticateToken(token) == false){
							System.err.println("Invalid token"); 	return null;}
		
		else{
		Patient patient = new Patient();	
			try {
				User user = auth.getUserByToken(token);
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

	/*
	 * Zugriffsbeschränkung: Doctor
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

		AuthenticationWSImpl auth = new AuthenticationWSImpl();
		if (auth.authenticateToken(token) == false) {System.err.println("Invalid token"); return "Authentifizierung fehlgeschlagen.";}
		
		// else if -- Doktor-Role-check 
		
		else{
			String response = null;
			try {
				User creatinguser = auth.getUserByToken(token);
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

	/*
	 * Das sollte niemand können, bzw. das Recht sollten höchstens Doktoren mit Schreibrecht haben
	 */
	
	@Transactional
	public String deleteCase(Accessor accessor) {
		int id;
		
		try {id = (int) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		else{
			String response = null;
			try {response = CaseDAO.deleteCase(id);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	/*
	 * Zugriffsbeschränkung: Doktoren mit Schreibrecht
	 */
	
	@Transactional
	public String updateCase(Accessor accessor) {
		Case pcase = new Case();
		
		try {pcase = (Case) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		
		String response = null;
		try {response = CaseDAO.updateCase(pcase);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
	}

}
