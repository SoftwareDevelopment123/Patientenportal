package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Medicine;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MedicineListResponse;
import de.patientenportal.persistence.MedicineDAO;

@WebService (endpointInterface = "de.patientenportal.services.MedicineWS")
public class MedicineWSImpl implements MedicineWS {

	@Transactional
	public Medicine getMedicine(Accessor accessor) {
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
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, false);
		if (authResponse != null) {
			System.err.println(authResponse);
			return null;
		}

		else {
			Medicine medi = new Medicine();
			try {
				medi = MedicineDAO.getMedicine(id);
			}
			catch (Exception e) {	System.err.println("Error: " + e);		return null;}
			return medi;
		}
	}

	@Transactional
	public String createMedicine(Accessor accessor) {
		Medicine medi = new Medicine();
		String token;
		
		try {
			medi = (Medicine) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (medi.getName()	== null)			{return "Bitte einen Namen angeben.";}
		if (medi.getDrugmaker()	== null)		{return "Kein Hersteller angegeben.";}
		if (medi.getActiveIngredient()	== null){return "Keine Angaben zu den Inhaltsstoffen.";}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, false);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		else { 
			String response = null;
			try {
				response = MedicineDAO.createMedicine(medi);
			} catch (Exception e) {
				System.err.println("Error: " + e); return "Error: " + e;
			}
			return response;
		}
	}

	@Transactional
	public String deleteMedicine(Accessor accessor) {
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
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, false);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}

		else{
			String response = null;
			try {response = MedicineDAO.deleteMedicine(id);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	@Transactional
	public String updateMedicine(Accessor accessor) {
		Medicine medi = new Medicine();
		String token;
		
		try {
			medi = (Medicine) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, false);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		else {
		String response = null;
		try {response = MedicineDAO.updateMedicine(medi);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	@Transactional
	public MedicineListResponse getAllMedicine(Accessor accessor) {
		MedicineListResponse response = new MedicineListResponse();
		String token;
		
		try {
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, false);
		if (authResponse != null) {
			System.err.println(authResponse);
			response.setResponseCode(authResponse);
			return response;
		}
		
		else {
			try {
				response.setResponseList(MedicineDAO.getAllMedicine());
				response.setResponseCode("success");
			}
			catch (Exception e) {
				System.err.println("Error: " + e);
				response.setResponseCode("Error: " + e);
				return response;}
		return response;
		}
	}
}