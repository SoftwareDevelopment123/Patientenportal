package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Contact;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.ContactDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.ContactWS")
public class ContactWSImpl implements ContactWS {

	/**
	 * <b>Kontaktdaten ändern</b><br>
	 * Über das token wird sichergestellt, dass nur eigene Daten geändert werden
	 * können.<br>
	 * 
	 * @param Accessor
	 *            mit <code>String</code> token und dem zu ändernden
	 *            <code>Contact</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 */
	@Transactional
	public String updateContact(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, InvalidParamException, AccessorException, PersistenceException {
		Contact contact = new Contact();
		String token;

		try {
			contact = (Contact) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (contact == null) {
			throw new InvalidParamException("No contact found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		User activeuser = AuthenticationWSImpl.getUserByToken(token);
		Contact usercontact = UserDAO.getUser(activeuser.getUserId()).getContact();

		if (usercontact.getContactID() != contact.getContactID()) {
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten ändern!";
		}

		else {
			String response = null;
			try {
				response = ContactDAO.updateContact(contact);
			} catch (Exception e) {
				throw new PersistenceException("Error 404: Database not found");
			}
			return response;
		}
	}

	/**
	 * <b>Kontaktdaten löschen</b><br>
	 * Über das token wird sichergestellt, dass nur eigene Daten gelöscht werden
	 * können.<br>
	 * 
	 * @param Accessor
	 *            mit <code>String</code> token und <code>int</code> contactID
	 *            des zu löschenden Kontakts
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws PersistenceException
	 */
	@Transactional
	public String deleteContact(Accessor accessor) throws AccessorException, InvalidParamException,
			AuthenticationException, AccessException, AuthorizationException, PersistenceException {
		int id;
		String token;

		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
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
		Contact usercontact = UserDAO.getUser(activeuser.getUserId()).getContact();

		if (usercontact.getContactID() != id) {
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten ändern!";
		}

		else {
			String response = null;
			try {
				response = ContactDAO.deleteContact(id);
			} catch (Exception e) {
				throw new PersistenceException("Error 404: Database not found");
			}
			return response;
		}
	}
}