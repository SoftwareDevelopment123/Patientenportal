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
	 * <b>Adressdaten �ndern</b><br>
	 * �ber das token wird sichergestellt, dass nur eigene Daten ge�ndert werden k�nnen.<br>
	 * 
	 * @param accessor mit <code>String</code> token und der zu �ndernden <code>Address</code>
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
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten �ndern!";
		}
		
		else {
		String response = null;
		try {response = AddressDAO.updateAddress(address);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	/**
	 * <b>Adressdaten l�schen</b><br>
	 * �ber das token wird sichergestellt, dass nur eigene Daten gel�scht werden k�nnen.<br>
	 * 
	 * @param Accessor mit <code>String</code> token und <code>int</code> addressID der zu l�schenden Adresse
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
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten �ndern!";
		}
		
		else {
			String response = null;
			try {response = AddressDAO.deleteAddress(id);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
			return response;
		}
	}
}