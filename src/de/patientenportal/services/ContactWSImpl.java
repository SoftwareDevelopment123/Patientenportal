package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Contact;
import de.patientenportal.persistence.ContactDAO;

@WebService (endpointInterface = "de.patientenportal.services.ContactWS")
public class ContactWSImpl implements ContactWS {

	/*
	 * Hinweis f�r die Pr�sentationsschicht
	 * Bei der Update-Methode ist sicherzustellen, dass das vollst�ndig Objekt mitgegeben wird,
	 * zum Beispiel durch Abruf der Kontaktdaten aus der Datenbank und �nderung einzelner Attribute
	 */
	
	@Transactional
	public String updateContact(Contact contact) {
		String response = ContactDAO.updateContact(contact);
		return response;
	}

	@Transactional
	public String deleteContact(int contactID) {
		String response = ContactDAO.deleteContact(contactID);
		return response;
	}
}
