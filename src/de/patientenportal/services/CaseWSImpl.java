package de.patientenportal.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Rights;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RightsDAO;
import de.patientenportal.persistence.UserDAO;

public class CaseWSImpl implements CaseWS {

	@Transactional
	public Case getCase(Accessor accessor) {
		int id;
		
		try {id = (int) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		else{
			Case pcase = new Case();
			try {pcase = CaseDAO.getCase(id);}
			catch (Exception e) {System.err.println("Error: " + e);}
		return pcase;
		}
	}

	/*
	 * Zugriffsbeschränkung: Patient
	 * eigentlich reicht hier das token aus (wäre auch sicherer)
	 * vorerst geben wir eine Patient-ID mit
	 */
	
	@Transactional
	public CaseListResponse getCases(Accessor accessor) {
		CaseListResponse response = new CaseListResponse();
		int id;
		
		try {id = (int) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		else{
			try {
			List<Case> rlist = PatientDAO.getPatient(id).getCases();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
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
				List<Doctor> doctors = new ArrayList<Doctor>();
					doctors.add(creatingdoctor);
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
