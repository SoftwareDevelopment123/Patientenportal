package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;

import org.hibernate.dialect.identity.SybaseAnywhereIdentityColumnSupport;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Medicine;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MedicineListResponse;
import de.patientenportal.persistence.MedicineDAO;
import smalltests.InvalidParamException;
import smalltests.TokenException;

@WebService (endpointInterface = "de.patientenportal.services.MedicineWS")
public class MedicineWSImpl implements MedicineWS {
	
	
	/**
	 * <b>Medikament über die ID abrufen</b><br>
	 * 
	 * Zugriffsbeschränkung: keine
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> medicineID
	 * @return <code>MedicationListResponse</code> mit der dem Patienten zugeordneten Medikation
	 */
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
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
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

	/**
	 * <b>Medikament hinzufügen</b><br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 * 
	 * @param accessor mit <code>String</code> token und dem anzulegenden Medikament
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws TokenException 
	 * @throws InvalidParamException 
	 */
	@Transactional
	public String createMedicine(Accessor accessor) throws InvalidParamException  {
		Medicine medi = new Medicine();
		String token;
		
		try {
			medi = (Medicine) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{throw new TokenException("No Token found");}
		if (medi.getName()	== null)			{throw new InvalidParamException("No Name found Hallo");}
		if (medi.getDrugmaker()	== null)		{throw new InvalidParamException("No Drugmaker found");}
		if (medi.getActiveIngredient()	== null){throw new InvalidParamException("No Active Ingredient found");}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
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

	/**
	 * <b>Medikament löschen</b><br>
	 *
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> MedicineID des zu löschenden Medikaments.
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
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
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
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

	/**
	 * <b>Medikament ändern</b><br>
	 *  
	 * Zugriffsbeschränkung: <code>Doctor</code> 
	 * 
	 * @param accessor mit <code>String</code> token und dem zu ändernden <code>Medicine</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
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
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
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

	/**
	 * <b>Alle Medikamente abrufen</b><br>
	 *
	 * Zugriffsbeschränkung: keine 
	 * 
	 * @param accessor mit <code>String</code> token 
	 * @return <code>MedicineListResponse</code> mit allen in der Datenbank vorhandenen Medikamenten
	 */
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
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
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