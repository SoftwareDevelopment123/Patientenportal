package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Office;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.OfficeDAO;

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
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws PersistenceException
	 */
	@Transactional
	public String createOffice(Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException {
		Office office = new Office();

		try {
			office = (Office) accessor.getObject();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
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

		else {
			String response = null;
			try {
				response = OfficeDAO.createOffice(office);
			} catch (Exception e) {
				throw new PersistenceException("Error 404: Database not found");
			}
			return response;
		}
	}

	/**
	 * <b>Arztpraxis löschen</b><br>
	 * 
	 * @param accessor
	 *            mit <code>int</code> officeID
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws PersistenceException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 */
	// TODO Sicherstellen, dass ein Arzt nur seine eigene Praxis löschen kann?
	@Transactional
	public String deleteOffice(Accessor accessor)
			throws PersistenceException, InvalidParamException, AccessorException {
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
			String response = null;
			try {
				response = OfficeDAO.deleteOffice(id);
			} catch (Exception e) {
				throw new PersistenceException("Error 404: Database not found");
			}
			return response;
		}
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
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws AccessorException
	 * @throws PersistenceException
	 */
	@Transactional
	public String updateOffice(Accessor accessor) throws AccessorException, PersistenceException {
		Office office = new Office();

		try {
			office = (Office) accessor.getObject();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}

		String response = null;
		try {
			response = OfficeDAO.updateOffice(office);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}
}
