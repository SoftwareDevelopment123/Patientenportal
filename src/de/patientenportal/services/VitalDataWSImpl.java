package de.patientenportal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.VitalDataListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.VitalDataDAO;

@WebService (endpointInterface = "de.patientenportal.services.VitalDataWS")
public class VitalDataWSImpl implements VitalDataWS {

	/**
	 * <b>Alle einem Behandlungsfall zugeordneten Vitaldaten nach Datentyp abrufen</b><br>
	 * Über das Token werden zu dem entsprechendem User die Leserechte abgefragt und die einsehbaren VitalDaten angezeigt.<br>
	 * 
	 * Zugriffsbeschränkung: keine
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> caseID
	 * @param vDataType (HEARTRATE, BLOODPRESSURE, BLOODSUGAR, WEIGHT)
	 * @return <code>VitalDataListResponse</code> mit den abgefragten Vitaldaten
	 */
	@Transactional
	public VitalDataListResponse getVitalDatabyC(Accessor accessor, VitalDataType vDataType) {
		VitalDataListResponse response = new VitalDataListResponse();
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
			List<VitalData> vdlist = new ArrayList<VitalData>();	
			List<VitalData> allVDlist = CaseDAO.getCase(id).getVitaldata();
			for(VitalData vd: allVDlist){
				if(vd.getVitalDataType() == vDataType){
					vdlist.add(vd);
				}
			}
			
			response.setResponseCode("success");
				response.setResponseList(vdlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}

	/**
	 * <b>Vitaldaten hinzufügen</b><br>
	 * Über das Token wird überprüft, ob der User über die entsprechenden Schreibrechte verfügt.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor, Patient</code>
	 * 
	 * @param accessor mit <code>String</code> token und den anzulegenden Vitaldaten
	 * @param VitalDataType vDataType
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String createVitalData(Accessor accessor) {
		VitalData vitalData = new VitalData();
		String token;
		
		try {
			vitalData = (VitalData) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) 						{return "Falscher Input";}
		if (token == null) 							{return "Kein Token angegeben";}
		if (vitalData.getValue()		== null)	{return "Bitte eine Wert angeben.";}
		if (vitalData.getVitalDataType()== null)	{return "Kein Vitaldatentyp zugeordnet.";}
		if (vitalData.getTimestamp()	== null)	{return "Kein Zeitstempel angegeben.";}
		if (vitalData.getPcase()		== null)	{return "Kein Behandlungsfall zugeordnet";}
	
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		accessor.setObject(vitalData.getPcase().getCaseID());
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		else { 
			String response = null;
			try {
				response = VitalDataDAO.add(vitalData);
			} catch (Exception e) {
				System.err.println("Error: " + e); return "Error: " + e;
			}
			return response;
		}
	}

	/**
	 * <b>Vitaldaten löschen</b><br>
	 * Über das token wird sichergestellt, dass nur Daten gelöscht werden können, 
	 * welche einem Fall mit Schreibrecht zugeordnet sind.<br>
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> VitalDataID der zu löschenden VitalDaten
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String deleteVitalData(Accessor accessor) {
		int id;
		String token;
		
		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		}
		catch (Exception e) {System.err.println("Invalid access"); 	return "Falscher Input";}
		if (token == null) 	{System.err.println("No token");		return "Kein Token angegeben";}
		if (id == 0) 		{System.err.println("Id null"); 		return "Keine ID angegeben";}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		else{
			String response = null;
			try {response = VitalDataDAO.deleteVitalData(id);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	/**
	 * <b>Vitaldaten ändern</b><br>
	 * Über das token wird sichergestellt, dass nur Daten geändert werden können, 
	 * welche einem Fall mit Schreibrecht zugeordnet sind.<br>
	 * 
	 * @param accessor mit <code>String</code> token und der zu ändernden <code>VitalData</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updateVitalData(Accessor accessor) {
		VitalData vitalData = new VitalData();
		String token;
		
		try {
			vitalData = (VitalData) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return "Falscher Input";}
		if (token == null) 	{System.err.println("No token");		return "Kein Token angegeben";}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		accessor.setObject(vitalData.getPcase().getCaseID());
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		else {
		String response = null;
		try {response = VitalDataDAO.updateVitalData(vitalData);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}

	}

}
