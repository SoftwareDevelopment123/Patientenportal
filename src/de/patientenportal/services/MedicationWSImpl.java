package de.patientenportal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Medication;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MedicationListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.MedicationWS")
public class MedicationWSImpl implements MedicationWS {

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
				response.setResponseCode("success");
				response.setResponseList(mlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}

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
	
	@Transactional
	public String createMedication(Accessor accessor) {
		//ActiveRole.Doctor
		//Access.WriteCase
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public String deleteMedication(Accessor accessor) {
		//ActiveRole.Doctor
		//Access.WriteCase
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public String updateMedication(Accessor accessor) {
		//ActiveRole.Doctor
		//Access.WriteCase
		// TODO Auto-generated method stub
		return null;
	}

}
