package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Office;
import de.patientenportal.persistence.OfficeDAO;

@WebService (endpointInterface = "de.patientenportal.services.OfficeWS")
public class OfficeWSImpl implements OfficeWS {

	@Transactional
	public Office getOffice(int officeID) {
		if (officeID == 0) {return null;}
		else{
			Office office = OfficeDAO.getOffice(officeID);
			return office;
		}
	}

	@Transactional
	public String createOffice(Office office) {
		if (office.getName()	== null)	{return "Bitte einen Namen angeben.";}
		if (office.getAddress()	== null)	{return "Keine Adresse angegeben.";}
		if (office.getContact()	== null)	{return "Keine Kontaktdaten angegeben.";}
		
		else{
			String response = OfficeDAO.createOffice(office);
			return response;
		}
	}

	@Transactional
	public String deleteOffice(int officeID) {
		String response = OfficeDAO.deleteOffice(officeID);
		return response;
	}

	/*
	 * Hinweis für die Präsentationsschicht
	 * Bei der Update-Methode ist sicherzustellen, dass das vollständig Objekte mitgegeben wird,
	 * zum Beispiel durch Abruf des Offices aus der Datenbank und Änderung einzelner Attribute
	 */
	
	@Transactional
	public String updateOffice(Office office) {
		String response = OfficeDAO.updateOffice(office);
		return response;
	}

}
