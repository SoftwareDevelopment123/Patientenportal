package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Office;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.OfficeDAO;

@WebService (endpointInterface = "de.patientenportal.services.OfficeWS")
public class OfficeWSImpl implements OfficeWS {

	
	@Transactional
	public Office getOffice(Accessor accessor) {
		int id;
		
		try {id = (int) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		else{
			Office office = new Office();
			try {office = OfficeDAO.getOffice(id);}
			catch (Exception e) {System.err.println("Error: " + e);}
		return office;
		}
	}

	@Transactional
	public String createOffice(Accessor accessor) {
		Office office = new Office();
		
		try {office = (Office) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		
		if (office.getName()	== null)	{return "Bitte einen Namen angeben.";}
		if (office.getAddress()	== null)	{return "Keine Adresse angegeben.";}
		if (office.getContact()	== null)	{return "Keine Kontaktdaten angegeben.";}
		
		else{
			String response = null;
			try {response = OfficeDAO.createOffice(office);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
			return response;
		}
	}
	
	@Transactional
	public String deleteOffice(Accessor accessor) {
		int id;
		
		try {id = (int) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		else{
			String response = null;
			try {response = OfficeDAO.deleteOffice(id);}
			catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	/*
	 * Hinweis für die Präsentationsschicht
	 * Bei der Update-Methode ist sicherzustellen, dass das vollständig Objekte mitgegeben wird,
	 * zum Beispiel durch Abruf des Offices aus der Datenbank und Änderung einzelner Attribute
	 */
	
	@Transactional
	public String updateOffice(Accessor accessor) {
		Office office = new Office();
		
		try {office = (Office) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		
		String response = null;
		try {response = OfficeDAO.updateOffice(office);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
	}
}
