package de.patientenportal.services;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Rights;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RightsListResponse;
import de.patientenportal.persistence.RightsDAO;


@WebService (endpointInterface = "de.patientenportal.services.RightsWS")
public class RightsWSImpl  implements RightsWS {
	
	/**
	 * <b>Alle Rechte zu einem Fall abrufen</b><br>
	 * Über das Token und die caseID wird das Schreibrecht abgefragt. Dient nur zur Anzeige der Information.<br>
	 * Die Schreibrechtprüfung findet bei den entsprechenden Methoden individuell statt.
	 *  
	 * @param accessor mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>RightsListResponse</code> mit Liste der Rechte und Erfolgsmeldung oder Fehlermeldung.
	 */
	@Transactional
	public RightsListResponse getRights(Accessor accessor) {
		RightsListResponse response = new RightsListResponse();
		int id;
		String token;
		
		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		}
		catch (Exception e) 						{System.err.println("Invalid access"); 	return null;}
		if (token == null) 							{System.err.println("No token");		return null;}
		if (id == 0) 								{System.err.println("Id null"); 		return null;}
		
		AuthenticationWSImpl auth = new AuthenticationWSImpl();
		if (auth.authenticateToken(token) == false)	{System.err.println("Invalid token"); 	return null;}
		//TODO Sicherstellen, dass der Fall zum Patienten gehört, welcher das Recht abruft
		else{
		
		List<Rights> rights = new ArrayList<Rights>();
		try { 
			rights = RightsDAO.getRights(id); 
			response.setResponseList(rights);
			response.setResponseCode("success");
		}
		catch (Exception e) {System.out.println("Error: " + e);}
		
		
		return response;
		}
	}
	
	/**
	 * <b>Erstellen eines Rechts</b>
	 * 
	 * @param accessor mit <code>String</code> Token und Rights-Entity mit dem zu erstellenden Recht <br>
	 * Parameter : 	<code>int</code> rightID <br>
					<code>Case</code> pcase <br>
					<code>Doctor</code> doctor ODER <code>Relative</code> relative <br>
					<code>boolean</code> rRight <br>
					<code>boolean</code> wRight <br>
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String createRight(Accessor accessor) {
		Rights right = new Rights();
		String token;
		
		try {
			right = (Rights) accessor.getObject();
			token = (String) accessor.getToken();
			}
		catch (Exception e) 												{System.err.println("Invalid access"); 	return null;}
		if (token == null) 													{System.err.println("No token");		return null;}
		if (right.getPcase()	== null									)	{return "Bitte einen Patientencase mit angeben.";}
		if (right.getDoctor()	== null && right.getRelative()== null	)	{return "Bitte geben Sie an für wen das Recht erteilt werden soll";}
		
		AuthenticationWSImpl auth = new AuthenticationWSImpl();
		if (auth.authenticateToken(token) == false){System.err.println("Invalid token"); 	return null;}
		//TODO Sicherstellen, dass der Fall zum Patienten gehört, welcher das Recht anlegt
		else{
			String response = null;
			try {response = RightsDAO.createRight(right);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
			return response;
		}
	}
	
	/**
	 *<b>Ändern eines Rechts</b><br>
	 *
	 * @param  accessor mit <code>String</code> Token und Rights-Entity des betroffenen Rechts
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updateRight(Accessor accessor) {
		Rights right = new Rights();
		String token;
		
		try {
			right = (Rights) accessor.getObject();
			token = (String) accessor.getToken();}
		catch (Exception e) 	{System.err.println("Invalid access"); return null;}
		if (token == null) 		{System.err.println("No token");		return null;}
		//TODO Sicherstellen, dass der Fall zum Patienten gehört, welcher das Recht ändert
		String response = null;
		try {response = RightsDAO.updateRight(right);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		
	}
	
	/**
	 * Entfernen eines Rechts <br>
	 * 
	 * @param accessor mit <code>String</code> Token und <code>int</code> rightID des betroffenen Rechts
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String deleteRight(Accessor accessor) {
		int id;
		
		try {id = (int) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		else{
			String response = null;
			try {response = RightsDAO.removeRight(id);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}
	
	
	
	

}
