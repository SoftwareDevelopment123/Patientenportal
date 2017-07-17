package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Contact;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.ContactDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.ContactWS")
public class ContactWSImpl implements ContactWS {

	/**
	 * <b>Kontaktdaten ändern</b><br>
	 * Über das token wird sichergestellt, dass nur eigene Daten geändert werden können.<br>
	 * 
	 * @param Accessor mit <code>String</code> token und dem zu ändernden <code>Contact</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updateContact(Accessor accessor) {
		Contact contact = new Contact();
		String token;

		try {
			contact = (Contact) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) 	{System.err.println("Invalid access");	return null;}
		if (token == null) 		{System.err.println("No token");		return null;}
		if (contact == null)	{System.err.println("No contact");		return null;}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		User activeuser = AuthenticationWSImpl.getUserByToken(token);
		Contact usercontact = UserDAO.getUser(activeuser.getUserId()).getContact();

		if (usercontact.getContactID() != contact.getContactID()){
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten ändern!";
		}
		
		else {
		String response = null;
		try {response = ContactDAO.updateContact(contact);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	/**
	 * <b>Kontaktdaten löschen</b><br>
	 * Über das token wird sichergestellt, dass nur eigene Daten gelöscht werden können.<br>
	 * 
	 * @param Accessor mit <code>String</code> token und <code>int</code> contactID des zu löschenden Kontakts
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String deleteContact(Accessor accessor) {
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
			return authResponse;
		}
		
		User activeuser = AuthenticationWSImpl.getUserByToken(token);
		Contact usercontact = UserDAO.getUser(activeuser.getUserId()).getContact();

		if (usercontact.getContactID() != id){
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten ändern!";
		}
		
		else {
			String response = null;
			try {response = ContactDAO.deleteContact(id);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
			return response;
		}
	}
}