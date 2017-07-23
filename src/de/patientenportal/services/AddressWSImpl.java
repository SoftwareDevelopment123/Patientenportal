package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Address;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.AddressDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.AddressWS")
public class AddressWSImpl implements AddressWS {

	
	/**
	 * <b>Adressdaten ändern</b><br>
	 * Über das token wird sichergestellt, dass nur eigene Daten geändert werden können.<br>
	 * 
	 * @param accessor mit <code>String</code> token und der zu ändernden <code>Address</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updateAddress(Accessor accessor) {
		Address address = new Address();
		String token;
		
		try {
			address = (Address) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) 	{System.err.println("Invalid access");	return null;}
		if (token == null) 		{System.err.println("No token");		return null;}
		if (address == null)	{System.err.println("No address");		return null;}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		User activeuser = AuthenticationWSImpl.getUserByToken(token);
		Address useraddress = UserDAO.getUser(activeuser.getUserId()).getAddress();

		if (useraddress.getAddressID() != address.getAddressID()){
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten ändern!";
		}
		
		else {
		String response = null;
		try {response = AddressDAO.updateAddress(address);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	/**
	 * <b>Adressdaten löschen</b><br>
	 * Über das token wird sichergestellt, dass nur eigene Daten gelöscht werden können.<br>
	 * 
	 * @param Accessor mit <code>String</code> token und <code>int</code> addressID der zu löschenden Adresse
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String deleteAddress(Accessor accessor) {
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
		Address useraddress = UserDAO.getUser(activeuser.getUserId()).getAddress();

		if (useraddress.getAddressID() != id){
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten ändern!";
		}
		
		else {
			String response = null;
			try {response = AddressDAO.deleteAddress(id);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
			return response;
		}
	}
}