package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Office;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.DoctorDAO;
import de.patientenportal.persistence.OfficeDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.OfficeWS")
public class OfficeWSImpl implements OfficeWS {

	/**
	 * <b>Arztpraxis über officeID abrufen</b><br>
	 * 
	 * @param accessor
	 *            mit <code>int</code> officeID
	 * @return <code>Office</code> office
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws PersistenceException
	 */
	@Transactional
	public Office getOffice(Accessor accessor) throws AccessorException, InvalidParamException, PersistenceException {
		int id;

		try {
			id = (int) accessor.getObject();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (id == 0) {
			throw new InvalidParamException("No ID found");
		}

		else {
			Office office = new Office();
			try {
				office = OfficeDAO.getOffice(id);
			} catch (Exception e) {
				throw new PersistenceException("Error 404: Database not found");
			}
			return office;
		}
	}

	/**
	 * <b>Neue Arztpraxis anlegen</b><br>
	 * 
	 * @param accessor
	 *            mit <code>Office</code> office
	 * @return <code>String</code> response mit Erfolgsmeldung
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws PersistenceException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public String createOffice(Accessor accessor) throws AccessorException, InvalidParamException, PersistenceException,
			AuthenticationException, AccessException, AuthorizationException {
		Office office = new Office();
		String token;

		try {
			office = (Office) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (office.getName() == null) {
			throw new InvalidParamException("No Name found");
		}
		if (office.getAddress() == null) {
			throw new InvalidParamException("No Address found");
		}
		if (office.getContact() == null) {
			throw new InvalidParamException("No Contact found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		String response = null;
		try {
			response = OfficeDAO.createOffice(office);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Arztpraxis löschen</b><br>
	 * 
	 * @param accessor
	 *            mit <code>int</code> officeID
	 * @return <code>String</code> response mit Erfolgsmeldung
	 * @throws PersistenceException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public String deleteOffice(Accessor accessor) throws PersistenceException, InvalidParamException, AccessorException,
			AuthenticationException, AccessException, AuthorizationException {
		int id;
		String token;

		try {
			id = (int) accessor.getId();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (id == 0) {
			throw new InvalidParamException("No ID found");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		String response = null;
		try {
			User user = AuthenticationWSImpl.getUserByToken(token);
			int officeId = UserDAO.getUser(user.getUserId()).getDoctor().getOffice().getOfficeID();
			if (id == officeId) {
				response = OfficeDAO.deleteOffice(id);
			} else
				throw new AccessException("You can only delete your own office!");
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	/*
	 * Hinweis für die Präsentationsschicht Bei der Update-Methode ist
	 * sicherzustellen, dass das vollständig Objekte mitgegeben wird, zum
	 * Beispiel durch Abruf des Offices aus der Datenbank und Änderung einzelner
	 * Attribute
	 */
	/**
	 * <b>Arztpraxis ändern</b><br>
	 * 
	 * @param accessor
	 *            mit <code>Office</code> office
	 * @return <code>String</code> response mit Erfolgsmeldung
	 * @throws TokenException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public String updateOffice(Accessor accessor) throws PersistenceException, InvalidParamException, AccessorException,
			AuthenticationException, AccessException, AuthorizationException {
		Office office = new Office();
		String token;
		int id;

		try {
			office = (Office) accessor.getObject();
			token = (String) accessor.getToken();
			id = (int) accessor.getId();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (id == 0) {
			throw new InvalidParamException("No ID found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		String response = null;
		try {
			int doctorId = AuthenticationWSImpl.getUserByToken(token).getDoctor().getDoctorID();
			int officeId = DoctorDAO.getDoctor(doctorId).getOffice().getOfficeID();
			if (id == officeId) {
				response = OfficeDAO.updateOffice(office);
			} else
				throw new AccessException("You can only update your own office!");
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}
}
