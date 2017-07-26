package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Address;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
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
	 * @throws AccessorException 
	 * @throws InvalidParamException 
	 * @throws AuthorizationException 
	 * @throws AccessException 
	 * @throws AuthenticationException 
	 * @throws PersistenceException 
	 */
	@Transactional
	public String updateAddress(Accessor accessor) throws AccessorException, InvalidParamException,
			AuthenticationException, AccessException, AuthorizationException, PersistenceException {
		Address address = new Address();
		String token;
		
		try {
			address = (Address) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (address == null) {
			throw new InvalidParamException("No Address found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		
		User activeuser = AuthenticationWSImpl.getUserByToken(token);
		Address useraddress = UserDAO.getUser(activeuser.getUserId()).getAddress();

		if (useraddress.getAddressID() != address.getAddressID()){
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten ändern!";
		}
		
		else {
		String response = null;
		try {response = AddressDAO.updateAddress(address);}
		catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
		}
	}

	/**
	 * <b>Adressdaten löschen</b><br>
	 * Über das token wird sichergestellt, dass nur eigene Daten gelöscht werden können.<br>
	 * 
	 * @param accessor mit <code>String</code> token und <code>int</code> addressID der zu löschenden Adresse
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws AccessorException 
	 * @throws InvalidParamException 
	 * @throws AuthorizationException 
	 * @throws AccessException 
	 * @throws AuthenticationException 
	 * @throws PersistenceException 
	 */
	@Transactional
	public String deleteAddress(Accessor accessor) throws AccessorException, InvalidParamException, AuthenticationException, AccessException, AuthorizationException, PersistenceException {
		int id;
		String token;
		
		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		}
		catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (id == 0) {
			throw new InvalidParamException("No ID found");
		}
		
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient, ActiveRole.Doctor, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		
		User activeuser = AuthenticationWSImpl.getUserByToken(token);
		Address useraddress = UserDAO.getUser(activeuser.getUserId()).getAddress();

		if (useraddress.getAddressID() != id){
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten ändern!";
		}
		
		else {
			String response = null;
			try {response = AddressDAO.deleteAddress(id);}
			catch (Exception e) {
				throw new PersistenceException("Error 404: Database not found");
			}
			return response;
		}
	}
}